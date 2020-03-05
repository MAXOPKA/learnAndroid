package com.example.learnandroid.utils.modules

import com.example.learnandroid.services.Preferences
import dagger.Module
import dagger.Provides

@Module
class PreferencesModule {
    @Provides
    fun providePreferences(): Preferences {
        return Preferences
    }
}
