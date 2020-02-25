package com.example.learnandroid.ui.screens.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.learnandroid.models.LoginModel
import com.example.learnandroid.services.API
import com.example.learnandroid.services.Database
import com.example.learnandroid.utils.DaggerAppComponent
import javax.inject.Inject
import com.example.learnandroid.services.api.requests.LoginRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel : ViewModel() {

    @Inject lateinit var apiService: API
    @Inject lateinit var databaseService: Database

    var textError = MutableLiveData<String?>()
    var textSuccess = MutableLiveData<String?>()
    var isLoading = MutableLiveData<Boolean>(false)

    init {
        DaggerAppComponent.create().injectLoginViewModel(this)
    }

    /* Actions */
    fun login(email: String, password: String) {
        textError.value = null

        if (!validateLogin(email, password)) {
            textError.value = "Error field values!"

            return
        }

        isLoading.value = true
        val loginData = LoginRequest(email, password)

        apiService.login(loginData)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                isLoading.value = false
                loginHandler(result)
            }, { error ->
                isLoading.value = false
                textError.value = "Error"
            })
    }

    /* Handlers */
    fun loginHandler(result: LoginModel) {
        if (result.error) {
            textError.value = "Error"
        } else {
            textError.value = null
            textSuccess.value = "Success! Token ${result.idToken}"
        }
    }

    /* Validators */
    fun validateLogin(email: String, password: String): Boolean {
        if (email.isEmpty()) return false
        if (password.isEmpty()) return false

        return true
    }
}
