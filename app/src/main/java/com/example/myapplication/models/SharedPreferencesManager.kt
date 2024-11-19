package com.example.myapplication.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object SharedPreferencesManager {
    private const val PREF_NAME = "AppSharedPreferences"
    lateinit var sharedPreferences: android.content.SharedPreferences
    val gson = Gson()

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
    fun saveBoolean(key:String,value:Boolean){
        sharedPreferences.edit().putBoolean(key,value).apply()
    }
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }
    fun clear(){
        sharedPreferences.edit().clear().apply()
    }
    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
    fun getAll(): Map<String, *> {
        return sharedPreferences.all
    }
    // MutableList methods
    inline fun <reified T> saveMutableList(key: String, list: MutableList<T>) {
        val json = gson.toJson(list)
        sharedPreferences.edit().putString(key, json).apply()
    }

    inline fun <reified T> getMutableList(key: String): MutableList<T> {
        val json = sharedPreferences.getString(key, null)
        return if (json != null) {
            gson.fromJson(json, object : TypeToken<MutableList<T>>() {}.type)
        } else {
            mutableListOf()
        }
    }
}