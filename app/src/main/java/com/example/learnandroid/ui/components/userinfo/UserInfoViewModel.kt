package com.example.learnandroid.ui.components.userinfo

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.learnandroid.models.CreateTransactionModel
import com.example.learnandroid.models.UserInfoModel
import com.example.learnandroid.services.api.utils.exceptions.UnauthorizedException
import com.example.learnandroid.ui.utils.baseui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserInfoViewModel() : BaseViewModel() {
    private var liveDataModel = MutableLiveData<UserInfoLiveDataModel>(
        UserInfoLiveDataModel(false, null, null)
    )

    init {
        apiService.userInfoOutput
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                getUserInfoHandler(result)
            }, { error ->
                super.errorHandler(error)
                getUserInfoErrorHandler(error)
            })

        apiService.loginOutput
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                getUserInfo()
            }, { error ->
                getUserInfoErrorHandler(error)
            })

        apiService.createTransactionOutput
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                createTransactionHandler(result)
            }, { error ->

            })
    }

    fun getUserInfoData(): MutableLiveData<UserInfoLiveDataModel>? {
        return liveDataModel
    }

    fun getUserInfo() {
        apiService.userInfo()
    }

    fun logout() {
        preferencesService.setAuthToken(null)
        super.errorHandler(UnauthorizedException(""))
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
}
