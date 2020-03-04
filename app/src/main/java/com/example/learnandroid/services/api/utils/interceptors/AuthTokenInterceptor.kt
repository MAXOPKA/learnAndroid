package com.example.learnandroid.services.api.utils.interceptors

import android.content.SharedPreferences
import com.example.learnandroid.services.API
import com.example.learnandroid.services.Preferences
import com.example.learnandroid.utils.DaggerAppComponent
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

object AuthTokenInterceptor: Interceptor {
    @Inject lateinit var preferences: Preferences

    init {
        DaggerAppComponent.create().injectAuthTokenInterceptor(this)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var newRequest: Request

        newRequest = request.newBuilder().build()

        preferences.gettoken.let {
            newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer ")
                .build()
        }

        return chain.proceed(newRequest)
    }
}