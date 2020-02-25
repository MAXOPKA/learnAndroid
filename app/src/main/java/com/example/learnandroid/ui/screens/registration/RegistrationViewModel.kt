package com.example.learnandroid.ui.screens.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.learnandroid.models.RegistrationModel
import com.example.learnandroid.services.API
import com.example.learnandroid.services.Database
import com.example.learnandroid.services.api.requests.RegistrationRequest
import com.example.learnandroid.services.api.responses.RegistrationResponse
import com.example.learnandroid.utils.DaggerAppComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class RegistrationViewModel() : ViewModel() {
    // TODO: Implement the ViewModel

    @Inject lateinit var apiService: API
    @Inject lateinit var databaseService: Database

    var textError = MutableLiveData<String?>()
    var textSuccess = MutableLiveData<String?>()
    var isLoading = MutableLiveData<Boolean>(false)

    init {
        DaggerAppComponent.create().injectRegistrationViewModel(this)
    }

    /* Actions */
    fun registration(name: String, email: String, password: String, passwordConfirmation: String) {
        textError.value = null

        if (!validateRegistration(name, email, password, passwordConfirmation)) {
            textError.value = "Error field values!"

            return
        }

        isLoading.value = true
        val registrationData = RegistrationRequest(name, email, password)

        apiService.registration(registrationData)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                isLoading.value = false
                registrationHandler(result)
            }, { error ->
                isLoading.value = false
                textError.value = "Error"
            })
    }

    /* Handlers */
    private fun registrationHandler(result: RegistrationModel) {
        if (result.error) {
            textError.value = "Error"
        } else {
            textError.value = null
            textSuccess.value = "Success! Token ${result.idToken}"
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
