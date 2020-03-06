package com.example.learnandroid.ui.components.usersList

import com.example.learnandroid.models.UserModel
import java.lang.Error

data class UsersListLiveDataModel(
    var users: List<UserModel>,
    var error: Boolean,
    var isLoading: Boolean
) {
}