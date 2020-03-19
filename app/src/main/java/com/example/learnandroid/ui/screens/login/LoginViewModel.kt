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


class LoginViewModel(apiService: IAPI, preferencesService: IPreferences) : BaseViewModel(apiService, preferencesService) {
    private var liveDataModel = MutableLiveData<LoginLiveDataModel>(LoginLiveDataModel(
        null,
        MessageTypes.ERROR,
        false
    ))

    init {
        if (preferencesService.getAuthToken() != null) {
            navigateToTransactions()
        }
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
        if (!validateLogin(email, password)) {
            liveDataModel.value = liveDataModel.value?.apply { messageText = "Error field values!" }

            return
        }

        liveDataModel.postValue(liveDataModel.value?.apply { isLoading = true })
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

    fun getLoginData(): MutableLiveData<LoginLiveDataModel>? {
        return liveDataModel
    }

    /* Handlers */
    private fun loginHandler(result: LoginModel) {
        liveDataModel.value?.isLoading = false

        if (result.error) {
            liveDataModel.value?.messageText = "Error"
            liveDataModel.value?.messageType = MessageTypes.ERROR
        } else {
            liveDataModel.value?.messageType = MessageTypes.SUCCESS
            liveDataModel.value?.messageText = "Success!"
        }

        // liveDataModel.postValue(liveDataModel.value)
        // navigateToTransactions()
    }

    private fun loginErrorHandler(error: Throwable) {
        liveDataModel.value?.isLoading = false
        liveDataModel.value?.messageText = "Error"
        liveDataModel.value?.messageType = MessageTypes.ERROR

        // liveDataModel.postValue(liveDataModel.value)
    }

    /* Validators */
    fun validateLogin(email: String, password: String): Boolean {
        if (email.isEmpty()) return false
        if (password.isEmpty()) return false

        return true
    }
}
