package com.example.learnandroid.ui.screens.confirmTransaction

import com.example.learnandroid.ui.utils.MessageTypes

data class ConfirmTransactionLiveDataModel(
    var messageText: String?,
    var messageType: MessageTypes?,
    var isLoading: Boolean
){}
