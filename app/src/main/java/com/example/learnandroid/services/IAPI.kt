package com.example.learnandroid.services

import com.example.learnandroid.models.*
import com.example.learnandroid.services.api.requests.CreateTransactionRequest
import com.example.learnandroid.services.api.requests.LoginRequest
import com.example.learnandroid.services.api.requests.RegistrationRequest
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

interface IAPI {
    val registrationOutput: PublishSubject<RegistrationModel>
    val loginOutput: PublishSubject<LoginModel>
    val transactionsOutput: PublishSubject<TransactionsModel>
    val userInfoOutput: PublishSubject<UserInfoModel>
    val usersListOutput: PublishSubject<UsersModel>
    val createTransactionOutput: PublishSubject<CreateTransactionModel>

    fun registration(requestData: RegistrationRequest)
    fun login(requestData: LoginRequest)
    fun transactions(page: Int, perPage: Int = 20)
    fun userInfo()
    fun usersList(key: String)
    fun createTransaction(userName: String, amount: Double)
}