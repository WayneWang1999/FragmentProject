package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.LesssonBinding
import com.example.myapplication.models.Lesson

// Adapter class to display lessons in a RecyclerView
class LessonListAdapter(
    private val myItems: MutableList<Lesson>,
    private val clickInterface: ClickDetectorInterface // Interface for click handling
) : RecyclerView.Adapter<LessonListAdapter.ViewHolder>() {

    // ViewHolder for each individual item
    inner class ViewHolder(val binding: LesssonBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the item layout using ViewBinding
        val binding = LesssonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return myItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currData: Lesson = this.myItems[position]

        // Bind data to the ViewHolder
        holder.binding.tvId.text = "${currData.id}"
        holder.binding.tvName.text = "${currData.name}"
        holder.binding.tvDescription.text = "${currData.description}"

        // Check if the lesson is finished and set an image accordingly
        if (currData.isFinished) {
            holder.binding.ivChecked.setImageResource(R.drawable.ic_action_check)
        }

        // Handle click event
        holder.binding.root.setOnClickListener {
            clickInterface.myClickFunction(position)  // Call the interface method
        }
    }

    // Method to update the list of lessons in the adapter
    fun updateLessons(newLessonList: MutableList<Lesson>) {
        this.myItems.clear()
        this.myItems.addAll(newLessonList)
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }
}
