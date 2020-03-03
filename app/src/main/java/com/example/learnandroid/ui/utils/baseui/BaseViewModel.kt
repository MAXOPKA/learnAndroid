package com.example.learnandroid.ui.utils.baseui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.learnandroid.ui.screens.login.LoginDataModel
import com.example.learnandroid.ui.utils.navigation.NavigationCommand

open class BaseViewModel : ViewModel() {
    private val navigationCommand = MutableLiveData<NavigationCommand>()

    fun getNavigationCommands(): MutableLiveData<NavigationCommand>? {
        return navigationCommand
    }

    fun navigate(directions: NavDirections) {
        navigationCommand.postValue(NavigationCommand.To(directions))
    }
}
