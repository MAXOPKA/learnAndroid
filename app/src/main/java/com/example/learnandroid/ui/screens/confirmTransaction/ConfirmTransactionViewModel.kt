package com.example.learnandroid.ui.screens.confirmTransaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.learnandroid.models.CreateTransactionModel
import com.example.learnandroid.models.LoginModel
import com.example.learnandroid.ui.screens.login.LoginDirections
import com.example.learnandroid.ui.screens.login.LoginLiveDataModel
import com.example.learnandroid.ui.utils.MessageTypes
import com.example.learnandroid.ui.utils.baseui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ConfirmTransactionViewModel : BaseViewModel() {
    private var liveDataModel = MutableLiveData<ConfirmTransactionLiveDataModel>(
        ConfirmTransactionLiveDataModel(
        null,
        MessageTypes.ERROR,
        false
    ))

    init {
        apiService.createTransactionOutput
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                createTransactionHandler(result)
            }, { error ->
                createTransactionErrorHandler(error)
            })
    }

    fun createTransaction(amountText: String?) {
        val amount = amountText?.toDoubleOrNull()

        if(amount != null) {
            apiService.createTransaction("123", amount)
        } else {
            liveDataModel.value?.isLoading = false
            liveDataModel.value?.messageText = "Invalid amount"
            liveDataModel.value?.messageType = MessageTypes.ERROR

            liveDataModel.postValue(liveDataModel.value)
        }
    }

    private fun navigateToTransactions() {
        navigate(ConfirmTransactionDirections.actionConfirmTransactionToTransactionsList())
    }

    /* Handlers */
    private fun createTransactionHandler(result: CreateTransactionModel) {
        liveDataModel.value?.isLoading = false

        if (result.error) {
            liveDataModel.value?.messageText = "Error"
            liveDataModel.value?.messageType = MessageTypes.ERROR
        } else {
            liveDataModel.value?.messageType = MessageTypes.SUCCESS
            liveDataModel.value?.messageText = "Success!"
        }

        liveDataModel.postValue(liveDataModel.value)
        navigateToTransactions()
    }

    private fun createTransactionErrorHandler(error: Throwable) {
        liveDataModel.value?.isLoading = false
        liveDataModel.value?.messageText = "Error"
        liveDataModel.value?.messageType = MessageTypes.ERROR

        liveDataModel.postValue(liveDataModel.value)
    }
}
