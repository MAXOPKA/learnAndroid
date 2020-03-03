package com.example.learnandroid.ui.screens.registration

import com.example.learnandroid.ui.utils.MessageTypes

data class RegistrationLiveDataModel(
    var messageText: String?,
    var messageType: MessageTypes,
    var isLoading: Boolean
) {
}