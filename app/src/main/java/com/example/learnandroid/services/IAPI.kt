package com.example.learnandroid.services

import com.example.learnandroid.models.*
import com.example.learnandroid.services.api.requests.LoginRequest
import com.example.learnandroid.services.api.requests.RegistrationRequest
import io.reactivex.Observable

interface IAPI {
    fun registration(requestData: RegistrationRequest): Observable<RegistrationModel>
    fun login(requestData: LoginRequest): Observable<LoginModel>
    fun transactions(page: Int, perPage: Int = 20): Observable<TransactionsModel>
    fun userInfo(): Observable<UserInfoModel>
    fun usersList(key: String): Observable<UsersModel>
}