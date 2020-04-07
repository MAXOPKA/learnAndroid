package com.example.learnandroid.services.api.utils.interceptors

import com.example.learnandroid.App
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
        App.instance.appComponent.injectHttpCodeInterceptor(this)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request());

        when(response.code()) {
            401 -> {
                if (response.body()?.string() != "No search string.") {
                    preferences.setAuthToken(null)

                    throw UnauthorizedException(response.body()?.string() ?: "User is not authorized");
                }
            }
            500, 502 -> throw InternalServerErrorException(response.body()?.string() ?: "An server error has occurred");
            400 -> throw BadRequestException(response.body()?.string() ?: "Invalid request");
        }

        return response;
    }
}
