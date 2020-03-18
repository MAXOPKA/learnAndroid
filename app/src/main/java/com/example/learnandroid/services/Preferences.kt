package com.example.learnandroid.services

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.learnandroid.App

object Preferences : IPreferences {
    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.instance)

    override fun setAuthToken(authToken: String?) {
        val editor = preferences.edit()
        editor.putString("authToken", authToken)
        editor.commit()
    }

    override fun getAuthToken(): String? {
        return preferences.getString("authToken", null)
    }
}