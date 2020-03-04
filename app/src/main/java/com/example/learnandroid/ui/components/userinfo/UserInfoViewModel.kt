package com.example.learnandroid.ui.components.userinfo

import androidx.lifecycle.MutableLiveData
import com.example.learnandroid.models.UserInfoModel
import com.example.learnandroid.ui.screens.login.LoginLiveDataModel
import com.example.learnandroid.ui.utils.baseui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserInfoViewModel : BaseViewModel() {

    private var liveDataModel = MutableLiveData<UserInfoLiveDataModel>(
        UserInfoLiveDataModel(false, null, null)
    )

    fun getUserInfoData(): MutableLiveData<UserInfoLiveDataModel>? {
        return liveDataModel
    }

    fun getUserInfo() {
        apiService.userInfo()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                getUserInfoHandler(result)
            }, { error ->
                super.errorHandler(error)
                getUserInfoErrorHandler(error)
            })
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
}
