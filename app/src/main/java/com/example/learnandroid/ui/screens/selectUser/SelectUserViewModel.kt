package com.example.learnandroid.ui.screens.selectUser

import com.example.learnandroid.services.IAPI
import com.example.learnandroid.services.IPreferences
import com.example.learnandroid.ui.components.searchbar.SearchBarViewModel
import com.example.learnandroid.ui.components.usersList.UsersListViewModel
import com.example.learnandroid.ui.screens.login.LoginDirections
import com.example.learnandroid.ui.utils.baseui.BaseViewModel

class SelectUserViewModel(apiService: IAPI, preferencesService: IPreferences) : BaseViewModel(apiService,
    preferencesService
) {

}
