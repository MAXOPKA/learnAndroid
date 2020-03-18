package com.example.learnandroid.utils.dagger

import com.example.learnandroid.mocks.services.APIMock
import com.example.learnandroid.services.IAPI
import dagger.Module
import dagger.Provides

@Module
internal object MockAPIModule {
    @Provides
    fun provideAPIService(api: APIMock): IAPI {
        return api
    }
}