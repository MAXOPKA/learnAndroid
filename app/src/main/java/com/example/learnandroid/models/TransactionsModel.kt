package com.example.learnandroid.models

data class TransactionsModel(
    val error: Boolean,
    val errorMessage: String? = null,
    val transactionsList: List<TransactionModel>? = null
) {
}