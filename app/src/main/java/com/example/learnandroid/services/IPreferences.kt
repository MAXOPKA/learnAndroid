package com.example.learnandroid.services

interface IPreferences {
    fun setAuthToken(authToken: String?)
    fun getAuthToken(): String?
}