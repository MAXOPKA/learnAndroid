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

    private val registrationInput = apiService.registrationOutput
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                registrationHandler(result)
            }, { error ->
                registrationErrorHandler(error)
            })

    /* Navigation */

    fun navigateToTransactions() {
        navigate(RegistrationDirections.actionRegistrationToTransactionsList())
    }

    /* Actions */
    fun registration(name: String, email: String, password: String, passwordConfirmation: String) {
        val validateError = validateRegistration(name, email, password, passwordConfirmation)

        if (!validateError.isNullOrBlank()) {
            liveDataModel.postValue(liveDataModel.value?.apply {
                messageText = validateError
                messageType = MessageTypes.ERROR
            })

            return
        }

        liveDataModel.postValue(liveDataModel.value?.apply {
            isLoading = true
        })

        val registrationData = RegistrationRequest(name, email, password)

        apiService.registration(registrationData)
    }

    /* Handlers */
    private fun registrationHandler(result: RegistrationModel) {
        if (result.error) {
            liveDataModel.postValue(liveDataModel.value?.apply {
                isLoading = false
                messageText = result.errorMessage
                messageType = MessageTypes.ERROR
            })
        } else {
            liveDataModel.postValue(liveDataModel.value?.apply {
                isLoading = false
                messageText = "Success!"
                messageType = MessageTypes.SUCCESS
            })

            navigateToTransactions()
        }


    }

    fun registrationErrorHandler(error: Throwable) {
        liveDataModel.postValue(liveDataModel.value?.apply {
            isLoading = false
            messageText = error.message
            messageType = MessageTypes.ERROR
        })
    }

    /* Validators */
    fun validateRegistration(name: String, email: String, password: String, passwordConfirmation: String): String? {
        if (name.isEmpty()) return "Name is empty"
        if (email.isEmpty()) return "Email is empty"
        if (password.isEmpty()) return "Password is empty"
        if (password != passwordConfirmation) return "The entered passwords are different"

        return null
    }

    override fun onCleared() {
        super.onCleared()

        registrationInput.dispose()
    }
}
