package com.example.learnandroid.services.api.responses

import com.example.learnandroid.models.TransactionModel

data class TransactionsResponse(val trans_token: List<Transaction>) {
}

data class Transaction(
    val id: Int,
    val date: String,
    val username: String,
    val amount: Double,
    val balance: Double
) {
    fun toModel(): TransactionModel {
        return TransactionModel(
            id,
            date,
            username,
            amount,
            balance
        )
    }
}