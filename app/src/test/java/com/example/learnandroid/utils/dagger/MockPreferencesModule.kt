package com.example.learnandroid.utils.dagger

import com.example.learnandroid.mocks.services.PreferencesMock
import com.example.learnandroid.services.IAPI
import com.example.learnandroid.services.IPreferences
import dagger.Module
import dagger.Provides

@Module
internal object MockPreferncesModule {
    @Provides
    fun providePreferences(preferences: PreferencesMock): IPreferences {
        return preferences
    }
}