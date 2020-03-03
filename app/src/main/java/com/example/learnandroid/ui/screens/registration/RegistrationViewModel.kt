package com.example.learnandroid.ui.screens.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.learnandroid.models.RegistrationModel
import com.example.learnandroid.services.API
import com.example.learnandroid.services.Database
import com.example.learnandroid.services.api.requests.RegistrationRequest
import com.example.learnandroid.ui.screens.login.LoginDirections
import com.example.learnandroid.ui.utils.MessageTypes
import com.example.learnandroid.ui.utils.baseui.BaseViewModel
import com.example.learnandroid.utils.DaggerAppComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class RegistrationViewModel() : BaseViewModel() {

    @Inject lateinit var apiService: API
    @Inject lateinit var databaseService: Database

    var liveDataModel = MutableLiveData<RegistrationLiveDataModel>(RegistrationLiveDataModel(
        null,
        MessageTypes.ERROR,
        false
    ))

    init {
        DaggerAppComponent.create().injectRegistrationViewModel(this)
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
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                registrationHandler(result)
            }, { error ->
                liveDataModel.value?.isLoading = false
                liveDataModel.value?.messageText = "Error"
                liveDataModel.value?.messageType = MessageTypes.ERROR
            })
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

    /* Validators */
    fun validateRegistration(name: String, email: String, password: String, passwordConfirmation: String): Boolean {
        if (name.isEmpty()) return false
        if (email.isEmpty()) return false
        if (password.isEmpty()) return false
        if (password != passwordConfirmation) return false

        return true
    }
}
