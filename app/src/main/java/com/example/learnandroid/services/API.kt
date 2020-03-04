package com.example.learnandroid.services

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.learnandroid.App
import com.example.learnandroid.models.*
import com.example.learnandroid.services.api.requests.LoginRequest
import com.example.learnandroid.services.api.requests.RegistrationRequest
import com.example.learnandroid.services.api.responses.LoginResponse
import com.example.learnandroid.services.api.responses.RegistrationResponse
import com.example.learnandroid.services.api.responses.TransactionsResponse
import com.example.learnandroid.services.api.responses.UserInfoResponse
import com.example.learnandroid.services.api.utils.Endpoints
import com.example.learnandroid.services.api.utils.interceptors.AuthTokenInterceptor
import com.example.learnandroid.services.api.utils.interceptors.HttpCodeInterceptor
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object API {
    private const val apiUrl: String = "http://193.124.114.46:3001"

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpCodeInterceptor)
        .addInterceptor(AuthTokenInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(apiUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    /* Queries */

    fun registration(requestData: RegistrationRequest): Observable<RegistrationModel> {
        val api: Endpoints = retrofit.create(Endpoints::class.java)

        return api.registration(requestData).map { result ->
            return@map registrationHandler(result)
        }
    }

    fun login(requestData: LoginRequest): Observable<LoginModel> {
        val api: Endpoints = retrofit.create(Endpoints::class.java)

        return api.login(requestData).map { result ->
            return@map loginHandler(result)
        }
    }

    fun transactions(page: Int, perPage: Int = 20): Observable<TransactionsModel> {
        val api: Endpoints = retrofit.create(Endpoints::class.java)

        return api.transactions().map { result ->
            return@map transactionsHandler(result)
        }
    }

    fun userInfo(): Observable<UserInfoModel> {
        val api: Endpoints = retrofit.create(Endpoints::class.java)

        return api.userInfo().map { result ->
            return@map userInfoHandler(result)
        }
    }

    /* Handlers */

    private fun registrationHandler(response: Response<RegistrationResponse>?): RegistrationModel {
        response?.let {
            setAuthToken(response.body()?.id_token)

            return RegistrationModel(!it.isSuccessful, response.body()?.id_token)
        }

        return RegistrationModel(true, null)
    }

    private fun loginHandler(response: Response<LoginResponse>?): LoginModel {
        response?.let {
            setAuthToken(response.body()?.id_token)

            return LoginModel(!it.isSuccessful, response.body()?.id_token)
        }

        return LoginModel(true, null)
    }

    private fun transactionsHandler(response: Response<TransactionsResponse>?): TransactionsModel {
        response?.let { it ->
            val transactions: List<TransactionModel> =
                it.body()?.trans_token?.map { it.toModel() } ?: emptyList()

            return TransactionsModel(!it.isSuccessful, null, transactions)
        }

        return TransactionsModel(true, null, null)
    }

    private fun userInfoHandler(response: Response<UserInfoResponse>?): UserInfoModel {
        response?.let { it ->
            return UserInfoModel(
                !it.isSuccessful,
                null,
                it.body()?.id,
                it.body()?.name,
                it.body()?.email,
                it.body()?.balance)
        }

        return UserInfoModel(true, null)
    }
}