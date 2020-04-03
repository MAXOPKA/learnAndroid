package com.example.learnandroid.models

data class CreateTransactionModel(val error: Boolean = false, val errorMessage: String?, val balance: Double? = null) {
}