package com.example.myapplication

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.ClickDetectorInterface
import com.example.myapplication.adapters.LessonListAdapter
import com.example.myapplication.databinding.FragmentLessonListBinding
import com.example.myapplication.models.Lesson
import com.example.myapplication.models.SharedPreferencesManager

/**
 * A simple [Fragment] subclass.
 * Use the [LessonListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LessonListFragment : Fragment(), ClickDetectorInterface {
    private lateinit var binding: FragmentLessonListBinding
    private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener
    private lateinit var adapterLesson: LessonListAdapter
    private val lessonList: MutableList<Lesson> = SharedPreferencesManager.getMutableList("LESSONLIST")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_lesson_list, container, false
        )

        // Setup RecyclerView
        adapterLesson = LessonListAdapter(lessonList, this)  // Pass 'this' (fragment)
        binding.rvLessonList.adapter = adapterLesson
        binding.rvLessonList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLessonList.addItemDecoration(
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        )

        // Register SharedPreferences listener
        listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == "LESSONLIST") {
                val updatedLessonList = SharedPreferencesManager.getMutableList<Lesson>("LESSONLIST")
                adapterLesson.updateLessons(updatedLessonList)
            }
        }
        SharedPreferencesManager.sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        return binding.root
    }

    override fun myClickFunction(position: Int) {

//        val userName = SharedPreferencesManager.getString(userKey) ?: ""
//        //  findNavController().navigate(R.id.action_inputNameFragment_to_welcomeBack)
//        val action = InputNameFragmentDirections.actionInputNameFragmentToWelcomeBack(userName)
//        findNavController().navigate(action)

        val lesson = lessonList[position]
        val action = LessonListFragmentDirections.actionLessonListToLessonDetail(
            lessonName = lesson.name,
            classIndex = position,
            lessonUrl = lesson.link
        )
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        SharedPreferencesManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }
}
