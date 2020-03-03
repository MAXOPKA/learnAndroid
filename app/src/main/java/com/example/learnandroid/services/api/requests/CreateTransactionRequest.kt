package com.example.learnandroid.services.api.requests

data class CreateTransactionRequest(
    val name: String,
    val amount: Double
) {
}