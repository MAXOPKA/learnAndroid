package com.example.learnandroid.ui.components.userinfo

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.learnandroid.models.UserInfoModel
import com.example.learnandroid.services.IAPI
import com.example.learnandroid.services.IPreferences
import com.example.learnandroid.ui.screens.login.LoginLiveDataModel
import com.example.learnandroid.ui.utils.baseui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserInfoViewModel(apiService: IAPI, preferencesService: IPreferences) : BaseViewModel(apiService,
    preferencesService
) {

    var userInfo: UserInfoModel = UserInfoModel(
        false,
        "",
        0,
        "",
        "",
        0.0
    )

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

    public fun logout(view: View) {
        val a = 4
    }

    private fun getUserInfoHandler(result: UserInfoModel) {
        userInfo.apply {
            error = result.error
            name = result.name
            balance = result.balance
        }

//        liveDataModel.value = liveDataModel.value?.apply {
//            this.error = result.error
//            name = result.name
//            balance = result.balance.toString()
//        }
    }

    private fun getUserInfoErrorHandler(error: Throwable) {
        userInfo.error = true
//        liveDataModel.value = liveDataModel.value?.apply { this.error = true }
    }
}
