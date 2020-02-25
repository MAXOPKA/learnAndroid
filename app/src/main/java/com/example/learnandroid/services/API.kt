package com.example.learnandroid.services

import com.example.learnandroid.models.LoginModel
import com.example.learnandroid.models.RegistrationModel
import com.example.learnandroid.services.api.requests.LoginRequest
import com.example.learnandroid.services.api.requests.RegistrationRequest
import com.example.learnandroid.services.api.responses.LoginResponse
import com.example.learnandroid.services.api.responses.RegistrationResponse
import com.example.learnandroid.services.api.utils.Endpoints
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object API {
    private val apiUrl: String = "http://193.124.114.46:3001"

    fun registration(requestData: RegistrationRequest): Observable<RegistrationModel> {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val api: Endpoints = retrofit.create(Endpoints::class.java)

        return api.registration(requestData).map { result ->
            return@map registrationHandler(result)
        }
    }

    fun login(requestData: LoginRequest): Observable<LoginModel> {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val api: Endpoints = retrofit.create(Endpoints::class.java)

        return api.login(requestData).map { result ->
            return@map loginHandler(result)
        }
    }

    private fun registrationHandler(response: Response<RegistrationResponse>?): RegistrationModel {
        response?.let {
            return RegistrationModel(!response.isSuccessful, response.body()?.id_token)
        }

        return RegistrationModel(true, null)
    }

    private fun loginHandler(response: Response<LoginResponse>?): LoginModel {
        response?.let {
            return LoginModel(!response.isSuccessful, response.body()?.id_token)
        }

        return LoginModel(true, null)
    }
}