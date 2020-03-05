package com.example.learnandroid.utils.modules

import com.example.learnandroid.services.API
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class APIModule {
    @Provides
    fun provideAPIService(): API {
        return API()
    }
}