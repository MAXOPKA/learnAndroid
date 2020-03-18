package com.example.learnandroid.utils.modules

import com.example.learnandroid.services.API
import com.example.learnandroid.services.IAPI
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class APIModule {
    @Provides
    fun provideAPIService(): IAPI {
        return API()
    }
}