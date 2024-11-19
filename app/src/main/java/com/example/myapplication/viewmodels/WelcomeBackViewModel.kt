package com.example.myapplication.viewmodels
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.models.Lesson
import com.example.myapplication.models.SharedPreferencesManager

class WelcomeBackViewModel(application: Application) : AndroidViewModel(application) {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _lessonProgress = MutableLiveData<String>()
    val lessonProgress: LiveData<String> get() = _lessonProgress

    init {
        SharedPreferencesManager.initialize(application)

        // Get the username from SharedPreferences
        _userName.value = SharedPreferencesManager.getString("USERNAME", "")

        // Calculate lesson progress
        calculateLessonProgress()
    }

    private fun calculateLessonProgress(): String {
        val lessonList = SharedPreferencesManager.getMutableList<Lesson>("LESSONLIST")
        val completedLessons = lessonList.count { it.isFinished }
        val remainingLessons = lessonList.size - completedLessons
        val progressPercentage = if (lessonList.isNotEmpty()) {
            (completedLessons * 100) / lessonList.size
        } else {
            0
        }

        return """
        You've completed $progressPercentage% of the
        Course!!
        Lessons Completed: $completedLessons
        Lessons Remaining: $remainingLessons
    """.trimIndent()
    }


    // Function to reset SharedPreferences and navigate back
    fun resetData() {
        SharedPreferencesManager.clear()
    }
    fun fetchLessonProgress() {
        // Simulate fetching updated progress
        _lessonProgress.value = calculateLessonProgress()
    }
}
