package com.example.learnandroid.utils

import androidx.lifecycle.ViewModel
import com.example.learnandroid.services.api.utils.interceptors.AuthTokenInterceptor
import com.example.learnandroid.ui.screens.login.LoginViewModel
import com.example.learnandroid.ui.screens.registration.RegistrationViewModel
import com.example.learnandroid.ui.screens.transactionsList.TransactionsListViewModel
import com.example.learnandroid.utils.modules.APIModule
import com.example.learnandroid.utils.modules.DatabaseModule
import dagger.Component

@Component(modules = [DatabaseModule::class, APIModule::class])
interface AppComponent {
    fun injectLoginViewModel(loginViewModel: LoginViewModel)
    fun injectRegistrationViewModel(registrationViewModel: RegistrationViewModel)
    fun injectTransactionsListViewModel(registrationViewModel: TransactionsListViewModel)
}