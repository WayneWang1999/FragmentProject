package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.models.Lesson
import com.example.myapplication.models.SharedPreferencesManager

class LessonDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _lessonList = MutableLiveData<List<Lesson>>()
    val lessonList: MutableLiveData<List<Lesson>> = _lessonList

    // LiveData for triggering UI actions like Toasts
    val toastMessage = MutableLiveData<String>()
    val navigateBack = MutableLiveData<Boolean>()

    init {
        loadLessons()
    }

    private fun loadLessons() {
        _lessonList.value = SharedPreferencesManager.getMutableList("LESSONLIST")
    }

    fun getLesson(lessonIndex: Int): Lesson? {
        return _lessonList.value?.getOrNull(lessonIndex)
    }

    fun markLessonAsComplete(lessonIndex: Int) {
        val lessonList = SharedPreferencesManager.getMutableList<Lesson>("LESSONLIST").toMutableList()

        if (lessonIndex in lessonList.indices) {
            lessonList[lessonIndex].isFinished = true
            SharedPreferencesManager.saveMutableList("LESSONLIST", lessonList)

            // Trigger navigation and show a Toast message
            navigateBack.value = true
            toastMessage.value = "Lesson marked as complete!"
        } else {
            toastMessage.value = "Invalid lesson index!"
        }
    }
}
