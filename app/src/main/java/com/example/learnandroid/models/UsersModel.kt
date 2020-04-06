package com.example.learnandroid.models

data class UsersModel(
    val error: Boolean,
    val errorMessage: String? = null,
    val usersList: List<UserModel>? = null
) {
}