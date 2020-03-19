package com.example.learnandroid.mocks.services

import com.example.learnandroid.models.*
import com.example.learnandroid.services.IAPI
import com.example.learnandroid.services.api.requests.LoginRequest
import com.example.learnandroid.services.api.requests.RegistrationRequest
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers

class APIMock : IAPI {
    override fun registration(requestData: RegistrationRequest): Observable<RegistrationModel> {
        val testObserver = TestObserver<RegistrationModel>()
        val result = Observable.just(RegistrationModel(error = true, errorMessage = "Bad request"))
        result.subscribe(testObserver)

        return result
    }

    override fun login(requestData: LoginRequest): Observable<LoginModel> {
        val result = Observable.just(LoginModel(error = false, errorMessage = null))

        return result
    }

    override fun transactions(page: Int, perPage: Int): Observable<TransactionsModel> {
        val testObserver = TestObserver<TransactionsModel>()
        val result = Observable.just(TransactionsModel(error = true, errorMessage = "Bad request",
            transactionsList = null
        ))
        result.subscribe(testObserver)

        return result
    }

    override fun userInfo(): Observable<UserInfoModel> {
        val testObserver = TestObserver<UserInfoModel>()
        val result = Observable.just(UserInfoModel(error = true, errorMessage = "Bad request"))
        result.subscribe(testObserver)

        return result
    }

    override fun usersList(key: String): Observable<UsersModel> {
        val testObserver = TestObserver<UsersModel>()
        val result = Observable.just(UsersModel(false, null, usersList = null))
        result.subscribe(testObserver)

        return result
    }
}
