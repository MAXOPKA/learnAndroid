package com.example.learnandroid

import android.app.Application
import android.content.Context
import com.example.learnandroid.utils.AppComponent
import com.example.learnandroid.utils.DaggerAppComponent
import dagger.android.AndroidInjection


class App: Application() {
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.create()
        instance = this
    }

    companion object {
        lateinit var instance: App
            private set
    }

    lateinit var appComponent: AppComponent
}