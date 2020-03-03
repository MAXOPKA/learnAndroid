package com.example.learnandroid.models

data class TransactionsModel(
    val error: Boolean,
    val errorMessage: String?,
    val transactionsList: List<TransactionModel>?
) {
}