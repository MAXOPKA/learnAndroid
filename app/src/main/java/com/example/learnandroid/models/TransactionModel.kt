package com.example.learnandroid.models

data class TransactionModel(
    val id: Int,
    val date: String,
    val username: String,
    val amount: Double,
    val balance: Double
) {

}