package com.example.myapplication.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLessonDetailBinding
import com.example.myapplication.viewmodels.LessonDetailViewModel

class LessonDetailFragment : Fragment() {

    private lateinit var binding: FragmentLessonDetailBinding
    private lateinit var viewModel: LessonDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_lesson_detail, container, false)

        viewModel = ViewModelProvider(this).get(LessonDetailViewModel::class.java)

        // Observe the lesson data
        val args = LessonDetailFragmentArgs.fromBundle(requireArguments())
        val lessonIndex = args.classIndex
        val lesson = viewModel.getLesson(lessonIndex)

        if (lesson != null) {
            binding.tvLessonName.text = lesson.name
        } else {
            Toast.makeText(requireContext(), "Lesson not found!", Toast.LENGTH_SHORT).show()
        }

        // Set up the "Watch Video" button
        binding.btnWatchVideo.setOnClickListener {
            val lessonUrl = args.lessonUrl
            if (!lessonUrl.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(lessonUrl))
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "No video URL available!", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up the "Complete" button
        binding.btnComplete.setOnClickListener {
            viewModel.markLessonAsComplete(lessonIndex)
        }

        // Observe for toast message and navigate back when required
        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        viewModel.navigateBack.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate) {
                findNavController().navigateUp()
            }
        }

        return binding.root
    }
}
