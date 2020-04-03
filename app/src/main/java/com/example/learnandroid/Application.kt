package com.example.learnandroid

import android.app.Application
import android.content.Context
import dagger.android.AndroidInjection


class App: Application() {
    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    companion object {
        lateinit var instance: App
            private set
    }
}