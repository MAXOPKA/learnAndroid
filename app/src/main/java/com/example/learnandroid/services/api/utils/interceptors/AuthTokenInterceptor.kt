package com.example.learnandroid.services.api.utils.interceptors

import com.example.learnandroid.services.IPreferences
import com.example.learnandroid.services.Preferences
import com.example.learnandroid.utils.DaggerAppComponent
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthTokenInterceptor : Interceptor {
    @Inject lateinit var preferences: IPreferences

    init {
        DaggerAppComponent.create().injectAuthTokenInterceptor(this)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var newRequest: Request

        newRequest = request.newBuilder().build()

        preferences.getAuthToken()?.let {
            newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer $it")
                .build()
        }

        return chain.proceed(newRequest)
    }
}