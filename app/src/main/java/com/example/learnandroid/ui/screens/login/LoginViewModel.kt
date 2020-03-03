package com.example.learnandroid.ui.screens.login

import android.R.attr.data
import androidx.lifecycle.MutableLiveData
import com.example.learnandroid.models.LoginModel
import com.example.learnandroid.services.API
import com.example.learnandroid.services.Database
import com.example.learnandroid.services.api.requests.LoginRequest
import com.example.learnandroid.ui.utils.MessageTypes
import com.example.learnandroid.ui.utils.baseui.BaseViewModel
import com.example.learnandroid.utils.DaggerAppComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class LoginViewModel : BaseViewModel() {

    @Inject lateinit var apiService: API
    @Inject lateinit var databaseService: Database

    private var liveDataModel = MutableLiveData<LoginDataModel>(LoginDataModel(
        null,
        MessageTypes.ERROR,
        false
    ))

    init {
        DaggerAppComponent.create().injectLoginViewModel(this)
    }

    /* Navigation */
    fun navigateToRegistration() {
        navigate(LoginDirections.actionLoginToRegistration())
    }

    fun navigateToTransactions() {
        navigate(LoginDirections.actionLoginToTransactionsList())
    }

    /* Actions */
    fun login(email: String, password: String) {
        if (!validateLogin(email, password)) {
            liveDataModel.value = liveDataModel.value?.apply { messageText = "Error field values!" }

            return
        }

        liveDataModel.value = liveDataModel.value?.apply { isLoading = true }
        val loginData = LoginRequest(email, password)

        apiService.login(loginData)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                loginHandler(result)
            }, { error ->
                loginErrorHandler(error)
            })
    }

    fun getLoginData(): MutableLiveData<LoginDataModel>? {
        return liveDataModel
    }

    /* Handlers */
    fun loginHandler(result: LoginModel) {
        liveDataModel.value?.isLoading = false

        if (result.error) {
            liveDataModel.value?.messageText = "Error"
            liveDataModel.value?.messageType = MessageTypes.ERROR
        } else {
            liveDataModel.value?.messageType = MessageTypes.SUCCESS
            liveDataModel.value?.messageText = "Success!"
        }

        liveDataModel.postValue(liveDataModel.value)
        navigateToTransactions()
    }

    fun loginErrorHandler(error: Throwable) {
        liveDataModel.value?.isLoading = false
        liveDataModel.value?.messageText = "Error"
        liveDataModel.value?.messageType = MessageTypes.ERROR

        liveDataModel.postValue(liveDataModel.value)
    }

    /* Validators */
    fun validateLogin(email: String, password: String): Boolean {
        if (email.isEmpty()) return false
        if (password.isEmpty()) return false

        return true
    }
}
