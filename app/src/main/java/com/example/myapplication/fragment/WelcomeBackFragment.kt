package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentWelcomeBackBinding
import com.example.myapplication.viewmodels.WelcomeBackViewModel

class WelcomeBackFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBackBinding

    // Use the ViewModel for the fragment
    private val viewModel: WelcomeBackViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_welcome_back, container, false
        )

        // Observe changes in userName from the ViewModel
        viewModel.userName.observe(viewLifecycleOwner, Observer { userName ->
            binding.tvWelBack.text = "Welcome Back, $userName!"
        })

        // Observe changes in lessonProgress from the ViewModel
        viewModel.lessonProgress.observe(viewLifecycleOwner, Observer { progress ->
            binding.tvComplete.text = progress
        })

        // Set button click listeners
        binding.btnContinue.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_welcomeBack_to_lessonList)
        )

        binding.btnReset.setOnClickListener {
            // Reset SharedPreferences and navigate back
            viewModel.resetData()
            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchLessonProgress() // Trigger recalculation in the ViewModel
    }
}
