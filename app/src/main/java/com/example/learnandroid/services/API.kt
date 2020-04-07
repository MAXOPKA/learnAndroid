package com.example.learnandroid.services

import com.example.learnandroid.App
import com.example.learnandroid.models.*
import com.example.learnandroid.services.api.requests.CreateTransactionRequest
import com.example.learnandroid.services.api.requests.LoginRequest
import com.example.learnandroid.services.api.requests.RegistrationRequest
import com.example.learnandroid.services.api.requests.UsersRequest
import com.example.learnandroid.services.api.responses.*
import com.example.learnandroid.services.api.utils.Endpoints
import com.example.learnandroid.services.api.utils.interceptors.AuthTokenInterceptor
import com.example.learnandroid.services.api.utils.interceptors.HttpCodeInterceptor
import com.example.learnandroid.utils.DaggerAppComponent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

open class API {
    @Inject
    lateinit var preferences: IPreferences

    private val apiUrl: String = "http://193.124.114.46:3001"

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpCodeInterceptor())
        .addInterceptor(AuthTokenInterceptor()
        )
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(apiUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    val api: Endpoints = retrofit.create(Endpoints::class.java)


    init {
        App.instance.appComponent.injectAPI(this)
    }
}