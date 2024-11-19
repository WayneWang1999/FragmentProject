package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapters.ClickDetectorInterface
import com.example.myapplication.adapters.LessonListAdapter
import com.example.myapplication.databinding.FragmentLessonListBinding
import com.example.myapplication.viewmodels.LessonListViewModel

class LessonListFragment : Fragment(), ClickDetectorInterface {

    private lateinit var binding: FragmentLessonListBinding
    private lateinit var adapterLesson: LessonListAdapter
    private val viewModel: LessonListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_lesson_list, container, false
        )

        // Observe the lesson list from the ViewModel
        viewModel.lessonList.observe(viewLifecycleOwner, Observer { lessons ->
            adapterLesson.updateLessons(lessons.toMutableList())
        })

        // Setup RecyclerView
        adapterLesson = LessonListAdapter(viewModel.lessonList.value?.toMutableList()?:  mutableListOf(), this)
        binding.rvLessonList.adapter = adapterLesson
        binding.rvLessonList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLessonList.addItemDecoration(
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        )

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchLessons() // Trigger a refresh of the lesson list
    }

    override fun myClickFunction(position: Int) {
        val lesson = viewModel.lessonList.value?.get(position)
        lesson?.let {
            val action = LessonListFragmentDirections.actionLessonListToLessonDetail(
                lessonName = it.name,
                classIndex = position,
                lessonUrl = it.link
            )
            findNavController().navigate(action)
        }
    }
}
