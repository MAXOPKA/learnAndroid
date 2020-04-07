package com.example.learnandroid.ui.screens.login

import androidx.lifecycle.MutableLiveData
import com.example.learnandroid.models.LoginModel
import com.example.learnandroid.services.IAPI
import com.example.learnandroid.services.IPreferences
import com.example.learnandroid.services.api.requests.LoginRequest
import com.example.learnandroid.ui.utils.MessageTypes
import com.example.learnandroid.ui.utils.baseui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LoginViewModel() : BaseViewModel() {
    private var liveDataModel = MutableLiveData<LoginLiveDataModel>(LoginLiveDataModel(
        null,
        MessageTypes.ERROR,
        false
    ))

    init {
        if (preferencesService.getAuthToken() != null) {
            navigateToTransactions()
        }

        accountsApiService.loginOutput
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                loginHandler(result)
            }, { error ->
                loginErrorHandler(error)
            })
    }

    /* Navigation */
    fun navigateToRegistration() {
        navigate(LoginDirections.actionLoginToRegistration())
    }

    private fun navigateToTransactions() {
        navigate(LoginDirections.actionLoginToTransactionsList())
    }

    /* Actions */
    fun login(email: String, password: String) {
        val validateError = validateLogin(email, password)

        if (!validateError.isNullOrBlank()) {
            liveDataModel.value = liveDataModel.value?.apply {
                messageText = validateError
                messageType = MessageTypes.ERROR
            }

            return
        }

        liveDataModel.postValue(liveDataModel.value?.apply { isLoading = true })
        val loginData = LoginRequest(email, password)

        accountsApiService.login(loginData)
    }

    fun getLoginData(): MutableLiveData<LoginLiveDataModel>? {
        return liveDataModel
    }

    /* Handlers */
    private fun loginHandler(result: LoginModel) {
        if (result.error) {
            liveDataModel.postValue(liveDataModel.value?.apply {
                isLoading = false
                messageText = result.errorMessage
                messageType = MessageTypes.ERROR
            })
        } else {
            liveDataModel.postValue(liveDataModel.value?.apply {
                isLoading = false
                messageType = MessageTypes.SUCCESS
                messageText = "Success!"
            })
            navigate(LoginDirections.actionLoginToTransactionsList())
        }
    }

    private fun loginErrorHandler(error: Throwable) {
        liveDataModel.value?.isLoading = false
        liveDataModel.value?.messageText = "Error"
        liveDataModel.value?.messageType = MessageTypes.ERROR

        liveDataModel.postValue(liveDataModel.value)
    }

    /* Validators */
    fun validateLogin(email: String, password: String): String? {
        if (email.isEmpty()) return "Email is empty"
        if (password.isEmpty()) return "Password is empty"

        return null
    }
}
