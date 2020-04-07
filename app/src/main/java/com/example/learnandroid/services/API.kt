package com.example.learnandroid.services

import com.example.learnandroid.App
import com.example.learnandroid.models.*
import com.example.learnandroid.services.api.requests.CreateTransactionRequest
import com.example.learnandroid.services.api.requests.LoginRequest
import com.example.learnandroid.services.api.requests.RegistrationRequest
import com.example.learnandroid.services.api.requests.UsersRequest
import com.example.learnandroid.services.api.responses.*
import com.example.learnandroid.services.api.utils.Endpoints
import com.example.learnandroid.services.api.utils.interceptors.AuthTokenInterceptor
import com.example.learnandroid.services.api.utils.interceptors.HttpCodeInterceptor
import com.example.learnandroid.utils.DaggerAppComponent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class API {
    @Inject
    lateinit var preferences: IPreferences

    private val apiUrl: String = "http://193.124.114.46:3001"

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpCodeInterceptor())
        .addInterceptor(AuthTokenInterceptor()
        )
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(apiUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private val api: Endpoints = retrofit.create(Endpoints::class.java)

    /* Outputs */

    val registrationOutput: PublishSubject<RegistrationModel> = PublishSubject.create()
    val loginOutput: PublishSubject<LoginModel> = PublishSubject.create()
    val transactionsOutput: PublishSubject<TransactionsModel> = PublishSubject.create()
    val userInfoOutput: PublishSubject<UserInfoModel> = PublishSubject.create()
    val usersListOutput: PublishSubject<UsersModel> = PublishSubject.create()
    val createTransactionOutput: PublishSubject<CreateTransactionModel> = PublishSubject.create()

    init {
        App.instance.appComponent.injectAPI(this)
    }

    /* Queries */

    fun registration(requestData: RegistrationRequest) {
        api.registration(requestData)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
            registrationHandler(it)
        }, {
                registrationOutput.onNext(RegistrationModel(true, it.message))
        })
    }

    fun login(requestData: LoginRequest) {
        api.login(requestData)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
            loginHandler(it)
        }, {
            loginOutput.onNext(LoginModel(true, it.localizedMessage))
        })
    }

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

    fun userInfo() {
        api.userInfo()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
            userInfoHandler(it)
        }, {
            userInfoOutput.onNext(UserInfoModel(true, it.message))
        })
    }

    fun usersList(key: String) {
        api.usersList(UsersRequest(key))
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
                usersHandler(it)
            }, {
                usersListOutput.onNext(UsersModel(true))
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

    private fun registrationHandler(response: Response<RegistrationResponse>?) {
        response?.let {
            preferences.setAuthToken(response.body()?.id_token)
            registrationOutput.onNext(RegistrationModel(!it.isSuccessful, response.body()?.id_token))

            return
        }

        registrationOutput.onNext(RegistrationModel(true, null))
    }

    private fun loginHandler(response: Response<LoginResponse>?) {
        response?.let {
            preferences.setAuthToken(response.body()?.id_token)
            loginOutput.onNext(LoginModel(!it.isSuccessful, response.body()?.id_token))

            return
        }

        loginOutput.onNext(LoginModel(true, null))
    }

    private fun transactionsHandler(response: Response<TransactionsResponse>?) {
        response?.let { it ->
            val transactions: List<TransactionModel> = it.body()?.trans_token?.map { it.toModel() } ?: emptyList()

            transactionsOutput.onNext(TransactionsModel(!it.isSuccessful, null, transactions))

            return
        }

        transactionsOutput.onNext(TransactionsModel(true))
    }

    private fun userInfoHandler(response: Response<UserInfoResponse>?) {
        response?.let { it ->
            userInfoOutput.onNext(UserInfoModel(
                !it.isSuccessful,
                null,
                it.body()?.user_info_token?.id,
                it.body()?.user_info_token?.name,
                it.body()?.user_info_token?.email,
                it.body()?.user_info_token?.balance))

            return
        }

        userInfoOutput.onNext(UserInfoModel(true, null))
    }

    private fun usersHandler(response: Response<List<User>>?) {
        response?.let { it ->
            val users: List<UserModel> = (it.body()?.map { it.toModel() }) ?: emptyList()
            usersListOutput.onNext(UsersModel(!it.isSuccessful, null, users))

            return
        }

        usersListOutput.onNext(UsersModel(true))
    }

    private fun createTransactionHandler(response: Response<CreateTransactionResponse>?) {
        response?.let { it ->
            createTransactionOutput.onNext(CreateTransactionModel(!it.isSuccessful, null, it.body()?.trans_token?.balance))

            return
        }

        createTransactionOutput.onNext(CreateTransactionModel(true, null))
    }
}