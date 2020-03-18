package com.example.learnandroid.ui.screens.transactionsList

import androidx.lifecycle.MutableLiveData
import com.example.learnandroid.models.TransactionsModel
import com.example.learnandroid.ui.utils.baseui.BaseViewModel
import com.example.learnandroid.utils.DaggerAppComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TransactionsListViewModel : BaseViewModel() {
    var liveDataModel = MutableLiveData<TransactionsListLiveDataModel>(
        TransactionsListLiveDataModel(emptyList())
    )

    /* Navigation */
    fun navigateToSelectUser() {
        navigate(TransactionsListDirections.actionTransactionsListToSelectUser())
    }

    /* Actions */
    fun getTransactions() {
        liveDataModel.value = liveDataModel.value?.apply {
            isLoading = true
        }

        apiService.transactions(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                getTransactionsHandler(result)
            }, { error ->
                super.errorHandler(error)
                getTransactionsErrorHandler()
            })
    }

    /*  Handlers */
    private fun getTransactionsHandler(result: TransactionsModel) {
        liveDataModel.value = liveDataModel.value?.apply {
            transactions = result.transactionsList ?: emptyList()
            isLoading = false
        }
    }

    private fun getTransactionsErrorHandler() {
        liveDataModel.value = liveDataModel.value?.apply {
            isLoading = false
        }
    }
}
