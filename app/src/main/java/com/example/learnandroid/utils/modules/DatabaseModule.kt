package com.example.learnandroid.utils.modules

import com.example.learnandroid.services.Database
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    fun provideAPIService(): Database {
        return Database
    }
}