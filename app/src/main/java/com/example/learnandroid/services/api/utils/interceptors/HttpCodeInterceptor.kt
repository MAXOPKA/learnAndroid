package com.example.learnandroid.services.api.utils.interceptors

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.learnandroid.App
import com.example.learnandroid.services.api.utils.exceptions.BadRequestException
import com.example.learnandroid.services.api.utils.exceptions.InternalServerErrorException
import com.example.learnandroid.services.api.utils.exceptions.UnauthorizedException
import okhttp3.Interceptor
import okhttp3.Response

object HttpCodeInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request());

        when(response.code()) {
            401 -> {
                val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.instance);
                val editor = preferences.edit()
                editor.putString("authToken", null)
                editor.commit()

                throw UnauthorizedException("User is not authorized");
            }
            500, 502 -> throw InternalServerErrorException("An server error has occurred");
            400 -> throw BadRequestException("Invalid request");
        }

        return response;
    }
}
