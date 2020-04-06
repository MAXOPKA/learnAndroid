package com.example.learnandroid.ui.utils.baseui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.learnandroid.App
import com.example.learnandroid.services.*
import com.example.learnandroid.services.api.utils.exceptions.UnauthorizedException
import com.example.learnandroid.ui.screens.login.LoginDirections
import com.example.learnandroid.ui.utils.navigation.NavigationCommand
import com.example.learnandroid.utils.DaggerAppComponent
import javax.inject.Inject
import kotlin.reflect.typeOf

open class BaseViewModel @Inject constructor () : ViewModel() {

    @Inject lateinit var apiService: API
    @Inject lateinit var preferencesService: IPreferences

    init {
        App.instance.appComponent.injectBaseViewModel(this)
    }

    private val navigationCommand = MutableLiveData<NavigationCommand>()

    fun getNavigationCommands(): MutableLiveData<NavigationCommand>? {
        return navigationCommand
    }

    fun navigate(directions: NavDirections) {
        navigationCommand.postValue(NavigationCommand.To(directions))
    }

    fun errorHandler(error: Throwable) {
        if (error is UnauthorizedException) {
            //navigate()
        }
    }
}
