package com.example.learnandroid.mocks.services

import com.example.learnandroid.services.IPreferences

object PreferencesMock : IPreferences {
    override fun setAuthToken(authToken: String?) {

    }

    override fun getAuthToken(): String? {
        return null
    }

}