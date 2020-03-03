package com.example.learnandroid.services.api.utils.interceptors

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.learnandroid.App
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.prefs.Preferences

object AuthTokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var newRequest: Request

        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.instance);
        val token = preferences.getString("authToken", null)

        newRequest = request.newBuilder().build()

        token.let {
            newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer $it")
                .build()
        }

        return chain.proceed(newRequest)
    }
}