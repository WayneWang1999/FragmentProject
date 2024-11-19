package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.models.Lesson
import com.example.myapplication.models.SharedPreferencesManager

class LessonListViewModel(application: Application) : AndroidViewModel(application) {

    // LiveData to hold the lesson list
    private val _lessonList = MutableLiveData<List<Lesson>>()
    val lessonList: LiveData<List<Lesson>> get() = _lessonList

    init {
        // Initialize SharedPreferences and load lessons
        SharedPreferencesManager.initialize(application)
        loadLessons()

        // Listen for SharedPreferences changes (lesson list update)
        SharedPreferencesManager.sharedPreferences.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == "LESSONLIST") {
                loadLessons()
            }
        }
    }

    // Load lessons from SharedPreferences
    private fun loadLessons() {
        val lessons = SharedPreferencesManager.getMutableList<Lesson>("LESSONLIST")
        _lessonList.postValue(lessons)
    }

    fun fetchLessons() {
        // Fetch or reload lessons (e.g., from SharedPreferences, database, or network)
        val lessons = SharedPreferencesManager.getMutableList<Lesson>("LESSONLIST") ?: emptyList()
        _lessonList.value = lessons
    }

    // Unregister the SharedPreferences listener
    override fun onCleared() {
        super.onCleared()
        SharedPreferencesManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener { _, key -> }
    }
}
