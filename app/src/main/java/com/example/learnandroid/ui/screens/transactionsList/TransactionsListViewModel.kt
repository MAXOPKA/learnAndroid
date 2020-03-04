package com.example.learnandroid.ui.screens.transactionsList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.learnandroid.models.TransactionModel
import com.example.learnandroid.models.TransactionsModel
import com.example.learnandroid.services.API
import com.example.learnandroid.services.Database
import com.example.learnandroid.services.api.responses.Transaction
import com.example.learnandroid.ui.screens.registration.RegistrationLiveDataModel
import com.example.learnandroid.ui.utils.MessageTypes
import com.example.learnandroid.ui.utils.baseui.BaseViewModel
import com.example.learnandroid.utils.DaggerAppComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TransactionsListViewModel : BaseViewModel() {
    var liveDataModel = MutableLiveData<TransactionsListLiveDataModel>(
        TransactionsListLiveDataModel(emptyList())
    )

    /* Actions */
    fun getTransactions() {
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
        liveDataModel.value = liveDataModel.value?.apply { transactions = result.transactionsList ?: emptyList() }
    }

    private fun getTransactionsErrorHandler() {

    }
}
