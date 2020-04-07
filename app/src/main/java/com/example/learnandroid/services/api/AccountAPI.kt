package com.example.learnandroid.services.api

import com.example.learnandroid.models.LoginModel
import com.example.learnandroid.models.RegistrationModel
import com.example.learnandroid.services.API
import com.example.learnandroid.services.api.requests.LoginRequest
import com.example.learnandroid.services.api.requests.RegistrationRequest
import com.example.learnandroid.services.api.responses.LoginResponse
import com.example.learnandroid.services.api.responses.RegistrationResponse
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import retrofit2.Response
import javax.inject.Singleton

@Singleton
class AccountAPI: API() {
    /* Outputs */

    val registrationOutput: PublishSubject<RegistrationModel> = PublishSubject.create()
    val loginOutput: PublishSubject<LoginModel> = PublishSubject.create()

    /* Queries */

    fun registration(requestData: RegistrationRequest) {
        api.registration(requestData)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
                registrationHandler(it)
            }, {
                registrationOutput.onNext(RegistrationModel(true, it.message))
            })
    }

    fun login(requestData: LoginRequest) {
        api.login(requestData)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
                loginHandler(it)
            }, {
                loginOutput.onNext(LoginModel(true, it.localizedMessage))
            })
    }

    /* Handlers */

    private fun registrationHandler(response: Response<RegistrationResponse>?) {
        response?.let {
            preferences.setAuthToken(response.body()?.id_token)
            registrationOutput.onNext(RegistrationModel(!it.isSuccessful, response.body()?.id_token))

            return
        }

        registrationOutput.onNext(RegistrationModel(true, null))
    }

    private fun loginHandler(response: Response<LoginResponse>?) {
        response?.let {
            preferences.setAuthToken(response.body()?.id_token)
            loginOutput.onNext(LoginModel(!it.isSuccessful, response.body()?.id_token))

            return
        }

        loginOutput.onNext(LoginModel(true, null))
    }
}