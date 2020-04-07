package com.example.learnandroid.utils.modules

import com.example.learnandroid.services.API
import com.example.learnandroid.services.IAPI
import com.example.learnandroid.services.api.AccountAPI
import com.example.learnandroid.services.api.TransactionsAPI
import com.example.learnandroid.services.api.UsersAPI
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class APIModule {
    @Provides
    @Singleton
    fun provideAccountAPIService(): AccountAPI {
        return AccountAPI()
    }

    @Provides
    @Singleton
    fun provideUsersAPIService(): UsersAPI {
        return UsersAPI()
    }

    @Provides
    @Singleton
    fun provideTransactionsAPIService(): TransactionsAPI {
        return TransactionsAPI()
    }
}