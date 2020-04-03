package com.example.learnandroid.utils.modules

import com.example.learnandroid.services.IPreferences
import com.example.learnandroid.services.Preferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferencesModule {
    @Provides
    @Singleton
    fun providePreferences(): IPreferences {
        return Preferences
    }
}
