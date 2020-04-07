package com.example.learnandroid.services.api

import com.example.learnandroid.models.CreateTransactionModel
import com.example.learnandroid.models.TransactionModel
import com.example.learnandroid.models.TransactionsModel
import com.example.learnandroid.services.API
import com.example.learnandroid.services.api.requests.CreateTransactionRequest
import com.example.learnandroid.services.api.responses.CreateTransactionResponse
import com.example.learnandroid.services.api.responses.TransactionsResponse
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import retrofit2.Response
import javax.inject.Singleton

@Singleton
class TransactionsAPI : API() {
    /* Outputs */

    val transactionsOutput: PublishSubject<TransactionsModel> = PublishSubject.create()
    val createTransactionOutput: PublishSubject<CreateTransactionModel> = PublishSubject.create()

    /* Queries */

    fun transactions(page: Int, perPage: Int = 20) {
        api.transactions()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
                transactionsHandler(it)
            }, {
                transactionsOutput.onNext(TransactionsModel(true))
            })
    }



    fun createTransaction(userName: String, amount: Double) {
        api.transactions(CreateTransactionRequest(userName, amount))
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
                createTransactionHandler(it)
            }, {
                createTransactionOutput.onError(it)
            })
    }

    /* Handlers */

    private fun transactionsHandler(response: Response<TransactionsResponse>?) {
        response?.let { it ->
            val transactions: List<TransactionModel> = it.body()?.trans_token?.map { it.toModel() } ?: emptyList()

            transactionsOutput.onNext(TransactionsModel(!it.isSuccessful, null, transactions))

            return
        }

        transactionsOutput.onNext(TransactionsModel(true))
    }



    private fun createTransactionHandler(response: Response<CreateTransactionResponse>?) {
        response?.let { it ->
            createTransactionOutput.onNext(CreateTransactionModel(!it.isSuccessful, null, it.body()?.trans_token?.balance))

            return
        }

        createTransactionOutput.onNext(CreateTransactionModel(true, null))
    }
}