package com.example.learnandroid.models

data class UsersModel(
    val error: Boolean,
    val errorMessage: String?,
    val usersList: List<UserModel>?
) {
}