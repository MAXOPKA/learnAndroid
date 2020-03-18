package com.example.learnandroid.services.api.utils.interceptors

import com.example.learnandroid.services.IPreferences
import com.example.learnandroid.services.Preferences
import com.example.learnandroid.services.api.utils.exceptions.BadRequestException
import com.example.learnandroid.services.api.utils.exceptions.InternalServerErrorException
import com.example.learnandroid.services.api.utils.exceptions.UnauthorizedException
import com.example.learnandroid.utils.DaggerAppComponent
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HttpCodeInterceptor: Interceptor {
    @Inject lateinit var preferences: IPreferences

    init {
        DaggerAppComponent.create().injectHttpCodeInterceptor(this)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request());

        when(response.code()) {
            401 -> {
                preferences.setAuthToken(null)

                throw UnauthorizedException("User is not authorized");
            }
            500, 502 -> throw InternalServerErrorException("An server error has occurred");
            400 -> throw BadRequestException("Invalid request");
        }

        return response;
    }
}
