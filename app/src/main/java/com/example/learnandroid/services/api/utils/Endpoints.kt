package com.example.learnandroid.services.api.utils

import com.example.learnandroid.services.api.requests.LoginRequest
import com.example.learnandroid.services.api.requests.RegistrationRequest
import com.example.learnandroid.services.api.responses.LoginResponse
import com.example.learnandroid.services.api.responses.RegistrationResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Endpoints {
    @POST(EndpointsUrls.registration)
    fun registration(@Body body: RegistrationRequest): Observable<Response<RegistrationResponse>>

    @POST(EndpointsUrls.login)
    fun login(@Body body: LoginRequest): Observable<Response<LoginResponse>>
}
