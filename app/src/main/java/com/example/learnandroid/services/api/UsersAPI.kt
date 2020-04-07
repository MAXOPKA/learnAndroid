package com.example.learnandroid.services.api

import com.example.learnandroid.models.UserInfoModel
import com.example.learnandroid.models.UserModel
import com.example.learnandroid.models.UsersModel
import com.example.learnandroid.services.API
import com.example.learnandroid.services.api.requests.UsersRequest
import com.example.learnandroid.services.api.responses.User
import com.example.learnandroid.services.api.responses.UserInfoResponse
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import retrofit2.Response
import javax.inject.Singleton

@Singleton
class UsersAPI: API() {
    /* Outputs */

    val userInfoOutput: PublishSubject<UserInfoModel> = PublishSubject.create()
    val usersListOutput: PublishSubject<UsersModel> = PublishSubject.create()

    /* Quries */

    fun userInfo() {
        api.userInfo()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
                userInfoHandler(it)
            }, {
                userInfoOutput.onNext(UserInfoModel(true, it.message))
            })
    }

    fun usersList(key: String) {
        api.usersList(UsersRequest(key))
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
                usersHandler(it)
            }, {
                usersListOutput.onNext(UsersModel(true))
            })
    }

    /* Handlers */

    private fun userInfoHandler(response: Response<UserInfoResponse>?) {
        response?.let { it ->
            userInfoOutput.onNext(UserInfoModel(
                !it.isSuccessful,
                null,
                it.body()?.user_info_token?.id,
                it.body()?.user_info_token?.name,
                it.body()?.user_info_token?.email,
                it.body()?.user_info_token?.balance))

            return
        }

        userInfoOutput.onNext(UserInfoModel(true, null))
    }

    private fun usersHandler(response: Response<List<User>>?) {
        response?.let { it ->
            val users: List<UserModel> = (it.body()?.map { it.toModel() }) ?: emptyList()
            usersListOutput.onNext(UsersModel(!it.isSuccessful, null, users))

            return
        }

        usersListOutput.onNext(UsersModel(true))
    }
}