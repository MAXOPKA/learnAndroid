package com.example.learnandroid.models

data class UserInfoModel(
    var error: Boolean,
    val errorMessage: String?,
    val id: Int? = null,
    var name: String? = null,
    val email: String? = null,
    var balance: Double? = null) {
}