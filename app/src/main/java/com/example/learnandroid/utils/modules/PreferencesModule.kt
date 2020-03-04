package com.example.learnandroid.utils.modules

import com.example.learnandroid.services.Database
import dagger.Module
import dagger.Provides

@Module
class PreferencesModule {
    @Provides
    fun provideAPIService(): Database {
        return Database
    }
}