package com.example.learnandroid.models

data class UserInfoModel(
    val error: Boolean,
    val errorMessage: String?,
    val id: Int? = null,
    val name: String? = null,
    val email: String? = null,
    val balance: Double? = null) {
}