package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.models.Lesson
import com.example.myapplication.models.SharedPreferencesManager

class InputNameViewModel(application: Application) : AndroidViewModel(application) {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _isFirstRun = MutableLiveData<Boolean>()
    val isFirstRun: LiveData<Boolean> get() = _isFirstRun

    // Changed from List<Lesson> to MutableList<Lesson> to fix the type mismatch
    private val _lessonList = MutableLiveData<MutableList<Lesson>>()
    val lessonList: LiveData<MutableList<Lesson>> get() = _lessonList

    init {
        SharedPreferencesManager.initialize(application)

        // Fetch saved username
        _userName.value = SharedPreferencesManager.getString("USERNAME", "")

        // Fetch the first run flag
        _isFirstRun.value = SharedPreferencesManager.getBoolean("isFirstRun", true)

        // Fetch or initialize lessons
        if (!SharedPreferencesManager.sharedPreferences.contains("LESSONLIST")) {
            val predefinedLessonList = mutableListOf(
                Lesson("1", "Introduction to Android", "Lesson Length: 12min", "https://youtu.be/2hIY1xuImuQ", false),
                Lesson("2", "Advanced Android", "Lesson Length: 12min", "https://youtu.be/3Ri9PPsGCEg", false),
                Lesson("3", "Advanced Android Compose", "Lesson Length: 12min", "https://youtu.be/3Ri9PPsGCEg", false),
                Lesson("4", "Advanced Android Lifecycle", "Lesson Length: 12min", "https://youtu.be/3Ri9PPsGCEg", false),
                Lesson("5", "Advanced Android Room", "Lesson Length: 12min", "https://youtu.be/3Ri9PPsGCEg", false),
                Lesson("6", "Advanced Android MVVM", "Lesson Length: 12min", "https://youtu.be/3Ri9PPsGCEg", false),
                Lesson("7", "Advanced Android Clean Arch", "Lesson Length: 12min", "https://youtu.be/3Ri9PPsGCEg", false),
                // Add other lessons here
            )
            SharedPreferencesManager.saveMutableList("LESSONLIST", predefinedLessonList)
            _lessonList.value = predefinedLessonList
        } else {
            _lessonList.value = SharedPreferencesManager.getMutableList("LESSONLIST")
        }
    }

    // Update the first run status and save the username
    fun saveUserName(userName: String) {
        SharedPreferencesManager.saveString("USERNAME", userName)
        _userName.value = userName
    }
    fun saveIsFirstRun(isFirstRun: Boolean) {
        SharedPreferencesManager.saveBoolean("isFirstRun", false)
        _isFirstRun.value = isFirstRun
    }
}
