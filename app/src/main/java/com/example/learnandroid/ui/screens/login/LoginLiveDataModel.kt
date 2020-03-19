package com.example.learnandroid.ui.screens.login

import com.example.learnandroid.ui.utils.MessageTypes

data class LoginLiveDataModel(
    var messageText: String?,
    var messageType: MessageTypes?,
    var isLoading: Boolean
) {
}