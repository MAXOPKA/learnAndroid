package com.example.learnandroid.ui.utils.baseui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.learnandroid.App
import com.example.learnandroid.services.*
import com.example.learnandroid.services.api.AccountAPI
import com.example.learnandroid.services.api.TransactionsAPI
import com.example.learnandroid.services.api.UsersAPI
import com.example.learnandroid.services.api.utils.exceptions.UnauthorizedException
import com.example.learnandroid.ui.screens.login.LoginDirections
import com.example.learnandroid.ui.utils.navigation.NavigationCommand
import com.example.learnandroid.utils.DaggerAppComponent
import com.example.learnandroid.utils.SingleLiveEvent
import javax.inject.Inject
import kotlin.reflect.typeOf

open class BaseViewModel @Inject constructor () : ViewModel() {

    @Inject lateinit var accountsApiService: AccountAPI
    @Inject lateinit var usersApiService: UsersAPI
    @Inject lateinit var transactionsApiService: TransactionsAPI
    @Inject lateinit var preferencesService: IPreferences

    init {
        App.instance.appComponent.injectBaseViewModel(this)
    }

    private var navigationCommand = SingleLiveEvent<NavigationCommand>()

    fun getNavigationCommands(): SingleLiveEvent<NavigationCommand>? {
        return navigationCommand
    }

    fun navigate(directions: NavDirections) {
        navigationCommand.postValue(NavigationCommand.To(directions))
    }

    fun navigateToRoot() {
        navigationCommand.postValue(NavigationCommand.ToRoot)
    }

    fun navigateToLogin() {
        navigationCommand.postValue(NavigationCommand.ToLogin)
    }

    fun navigateToTransactionsList() {
        navigationCommand.postValue(NavigationCommand.ToTransactionsList)
    }

    fun navigateBack() {
        navigationCommand.postValue(NavigationCommand.Back)
    }

    fun errorHandler(error: Throwable) {
        if (error is UnauthorizedException) {
            navigateToLogin()
        }
    }

    override fun onCleared() {
        super.onCleared()

    }
}
