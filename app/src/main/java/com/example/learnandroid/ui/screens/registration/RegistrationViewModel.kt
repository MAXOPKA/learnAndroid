package com.example.learnandroid.ui.screens.registration

import androidx.lifecycle.MutableLiveData
import com.example.learnandroid.models.RegistrationModel
import com.example.learnandroid.services.IAPI
import com.example.learnandroid.services.IPreferences
import com.example.learnandroid.services.api.requests.RegistrationRequest
import com.example.learnandroid.ui.utils.MessageTypes
import com.example.learnandroid.ui.utils.baseui.BaseViewModel
import com.example.learnandroid.utils.DaggerAppComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class RegistrationViewModel() : BaseViewModel() {
    var liveDataModel = MutableLiveData<RegistrationLiveDataModel>(RegistrationLiveDataModel(
        null,
        MessageTypes.ERROR,
        false
    ))

    init {
        apiService.registrationOutput
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                registrationHandler(result)
            }, { error ->
                registrationErrorHandler(error)
            })
    }

    /* Navigation */

    fun navigateToTransactions() {
        navigate(RegistrationDirections.actionRegistrationToTransactionsList())
    }

    /* Actions */
    fun registration(name: String, email: String, password: String, passwordConfirmation: String) {
        liveDataModel.value?.messageText = null

        if (!validateRegistration(name, email, password, passwordConfirmation)) {
            liveDataModel.value?.messageText = "Error field values!"
            liveDataModel.value?.messageType = MessageTypes.ERROR

            return
        }

        liveDataModel.value?.isLoading = true
        val registrationData = RegistrationRequest(name, email, password)

        apiService.registration(registrationData)
    }

    /* Handlers */
    private fun registrationHandler(result: RegistrationModel) {
        liveDataModel.value?.isLoading = false
        if (result.error) {
            liveDataModel.value?.messageText = "Error"
            liveDataModel.value?.messageType = MessageTypes.ERROR
        } else {
            liveDataModel.value?.messageText = "Success!"
            liveDataModel.value?.messageType = MessageTypes.SUCCESS
        }
    }

    fun registrationErrorHandler(error: Throwable) {
        liveDataModel.value?.isLoading = false
        liveDataModel.value?.messageText = "Error"
        liveDataModel.value?.messageType = MessageTypes.ERROR
    }

    /* Validators */
    fun validateRegistration(name: String, email: String, password: String, passwordConfirmation: String): Boolean {
        if (name.isEmpty()) return false
        if (email.isEmpty()) return false
        if (password.isEmpty()) return false
        if (password != passwordConfirmation) return false

        return true
    }
}
