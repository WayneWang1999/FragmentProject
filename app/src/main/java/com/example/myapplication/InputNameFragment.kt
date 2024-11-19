package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentInputNameBinding
import com.example.myapplication.models.Lesson
import com.example.myapplication.models.SharedPreferencesManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InputNameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InputNameFragment : Fragment() {

    private lateinit var binding: FragmentInputNameBinding

    // SharedPreferences keys
    private val userKey = "USERNAME"
    private val firstRunKey = "isFirstRun"
    private val lessonKey = "LESSONLIST"

    // Predefined lesson list
    private val lessonList: MutableList<Lesson> = mutableListOf(
        Lesson("1", "Introduction to Android", "Lesson Length:12min", "https://youtu.be/2hIY1xuImuQ", false),
        Lesson("2", "Advanced Android", "Lesson Length:12min", "https://youtu.be/3Ri9PPsGCEg", false),
        Lesson("3", "Introduction to Web Development", "Lesson Length:12min", "https://youtu.be/W6NZfCO5SIk", false),
        Lesson("4", "Introduction to Kotlin", "Lesson Length:12min", "https://youtu.be/2a8PqmNKwTs", false),
        Lesson("5", "Advanced Web Development", "Lesson Length:12min", "https://youtu.be/2a8PqmNKwTs", false)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_input_name, container, false)

        // Initialize SharedPreferencesManager
        SharedPreferencesManager.initialize(requireContext())
      //  SharedPreferencesManager.saveBoolean(firstRunKey, true)

        // Save lessons if not already saved
        if (!SharedPreferencesManager.sharedPreferences.contains(lessonKey)) {
            SharedPreferencesManager.saveMutableList(lessonKey, lessonList)
        }

        // Handle first run or navigate to WelcomeBack
        if (isFirstRun()) {
            handleFirstRun()
        } else {
            navigateToWelcomeBack()
        }

        return binding.root
    }

    private fun isFirstRun(): Boolean {
        return SharedPreferencesManager.getBoolean(firstRunKey, true) ||
                SharedPreferencesManager.getString(userKey, "").isEmpty()
    }

    private fun handleFirstRun() {
        binding.btnSignup.setOnClickListener {
            val userName = binding.etName.text.toString().trim()
            if (userName.isNotBlank()) {
                SharedPreferencesManager.saveString(userKey, userName)
                SharedPreferencesManager.saveBoolean(firstRunKey, false)
                navigateToWelcomeBack() // Reuse navigation method
            } else {
                Toast.makeText(requireContext(), "Please input the username", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToWelcomeBack() {
        val userName = SharedPreferencesManager.getString(userKey) ?: ""
        //  findNavController().navigate(R.id.action_inputNameFragment_to_welcomeBack)
        val action = InputNameFragmentDirections.actionInputNameFragmentToWelcomeBack(userName)
        findNavController().navigate(action)

        //Navigation.createNavigateOnClickListener(R.id.action_inputNameFragment_to_welcomeBack)
    }
}
