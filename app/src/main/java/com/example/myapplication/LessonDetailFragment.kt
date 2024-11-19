package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentLessonDetailBinding
import com.example.myapplication.models.Lesson
import com.example.myapplication.models.SharedPreferencesManager


/**
 * A simple [Fragment] subclass.
 * Use the [LessonDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LessonDetailFragment : Fragment() {
    private lateinit var binding: FragmentLessonDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson_detail, container, false)

        // Retrieve the lesson data from arguments
        // Get arguments using SafeArgs
        val args = LessonDetailFragmentArgs.fromBundle(requireArguments())

        val lessonName = args.lessonName
        val lessonIndex = args.classIndex
        val lessonUrl = args.lessonUrl

        // Validate and handle missing data
        if (lessonIndex == null || lessonIndex == -1) {
            Toast.makeText(requireContext(), "Invalid lesson index!", Toast.LENGTH_LONG).show()
            return binding.root
        }

        if (lessonName.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Lesson name not found!", Toast.LENGTH_LONG).show()
            return binding.root
        }

        // Set the lesson name in the UI
        binding.tvLessonName.text = lessonName

        // Set up the "Watch Video" button
        binding.btnWatchVideo.setOnClickListener {
            if (!lessonUrl.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(lessonUrl))
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "No video URL available!", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up the "Complete" button
        binding.btnComplete.setOnClickListener {
            lessonIndex?.let {
                markLessonAsComplete(it)
            }
        }

        return binding.root
    }

    private fun markLessonAsComplete(lessonIndex: Int) {
        // Retrieve the current list of lessons from SharedPreferences
        val lessonList = SharedPreferencesManager.getMutableList<Lesson>("LESSONLIST")

        // Update the lesson's completion status
        if (lessonIndex in lessonList.indices) {
            lessonList[lessonIndex].isFinished = true
            SharedPreferencesManager.saveMutableList("LESSONLIST", lessonList)
            // Navigate back
            findNavController().navigateUp()
            Toast.makeText(requireContext(), "Lesson marked as complete!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Invalid lesson index!", Toast.LENGTH_SHORT).show()
        }
    }
}