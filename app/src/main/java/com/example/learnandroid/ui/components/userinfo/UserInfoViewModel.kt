package com.example.learnandroid.ui.components.userinfo

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.learnandroid.models.CreateTransactionModel
import com.example.learnandroid.models.UserInfoModel
import com.example.learnandroid.services.api.utils.exceptions.UnauthorizedException
import com.example.learnandroid.ui.utils.baseui.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UserInfoViewModel() : BaseViewModel() {
    private var liveDataModel = MutableLiveData<UserInfoLiveDataModel>(
        UserInfoLiveDataModel(false, null, null)
    )

    private val userInfoInput = usersApiService.userInfoOutput
    .observeOn(AndroidSchedulers.mainThread())
    .subscribeOn(Schedulers.io())
    .subscribe ({ result ->
        getUserInfoHandler(result)
    }, { error ->
        super.errorHandler(error)
        getUserInfoErrorHandler(error)
    })

    private val loginInput = accountsApiService.loginOutput
    .observeOn(AndroidSchedulers.mainThread())
    .subscribeOn(Schedulers.io())
    .subscribe ({ result ->
        getUserInfo()
    }, { error ->
        getUserInfoErrorHandler(error)
    })

    private val registrationInput = accountsApiService.registrationOutput
    .observeOn(AndroidSchedulers.mainThread())
    .subscribeOn(Schedulers.io())
    .subscribe ({ result ->
        getUserInfo()
    }, { error ->
        getUserInfoErrorHandler(error)
    })

    private val createTransactionInput = transactionsApiService.createTransactionOutput
    .observeOn(AndroidSchedulers.mainThread())
    .subscribeOn(Schedulers.io())
    .subscribe ({ result ->
        createTransactionHandler(result)
    }, { error ->

    })

    fun getUserInfoData(): MutableLiveData<UserInfoLiveDataModel>? {
        return liveDataModel
    }

    fun getUserInfo() {
        usersApiService.userInfo()
    }

    fun logout() {
        preferencesService.setAuthToken(null)
        liveDataModel.value = liveDataModel.value?.apply { this.error = true }
        navigateToLogin()
    }

    private fun getUserInfoHandler(result: UserInfoModel) {
        liveDataModel.value = liveDataModel.value?.apply {
            this.error = result.error
            name = result.name
            balance = result.balance.toString()
        }
    }

    private fun getUserInfoErrorHandler(error: Throwable) {
        liveDataModel.value = liveDataModel.value?.apply { this.error = true }
    }

    private fun createTransactionHandler(result: CreateTransactionModel) {
        liveDataModel.value = liveDataModel.value?.apply {
            this.error = result.error
            balance = result.balance.toString()
        }
    }

    override fun onCleared() {
        super.onCleared()

        userInfoInput.dispose()
        loginInput.dispose()
        registrationInput.dispose()
        createTransactionInput.dispose()
    }
}
