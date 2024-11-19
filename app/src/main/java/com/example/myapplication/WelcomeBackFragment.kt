package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentWelcomeBackBinding
import com.example.myapplication.models.Lesson
import com.example.myapplication.models.SharedPreferencesManager


/**
 * A simple [Fragment] subclass.
 * Use the [WelcomeBackFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeBackFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBackBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_welcome_back, container, false
        )

        // Get the username from the Safe Args
        // Get the argument using SafeArgs
        val args = WelcomeBackFragmentArgs.fromBundle(requireArguments())
        val userName = args.userName

        // Set the welcome message
        binding.tvWelBack.text = "Welcome Back, $userName!"

        binding.btnContinue.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_welcomeBack_to_lessonList)
        )

        // Calculate completed and remaining lessons
        calculateLessonProgress()

        // Reset button: Clear SharedPreferences and finish the activity
        binding.btnReset.setOnClickListener {
            SharedPreferencesManager.clear()
            findNavController().navigateUp()
            //finish() // Go back to the previous activity
        }

        return binding.root
    }

    private fun calculateLessonProgress() {
        val lessonList = SharedPreferencesManager.getMutableList<Lesson>("LESSONLIST")
        val completedLessons = lessonList.count { it.isFinished }
        val remainingLessons = lessonList.size - completedLessons
        val progressPercentage = if (lessonList.isNotEmpty()) {
            (completedLessons * 100) / lessonList.size
        } else {
            0
        }

        binding.tvComplete.text = """
            You've completed $progressPercentage% of the
            Course!!
            Lessons Completed: $completedLessons
            Lessons Remaining: $remainingLessons
        """.trimIndent()
    }
}
