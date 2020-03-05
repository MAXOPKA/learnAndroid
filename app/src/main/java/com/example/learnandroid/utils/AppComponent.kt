package com.example.learnandroid.utils

import com.example.learnandroid.services.API
import com.example.learnandroid.services.api.utils.interceptors.AuthTokenInterceptor
import com.example.learnandroid.services.api.utils.interceptors.HttpCodeInterceptor
import com.example.learnandroid.ui.utils.baseui.BaseViewModel
import com.example.learnandroid.utils.modules.APIModule
import com.example.learnandroid.utils.modules.DatabaseModule
import com.example.learnandroid.utils.modules.PreferencesModule
import dagger.Component

@Component(modules = [DatabaseModule::class, APIModule::class, PreferencesModule::class])
interface AppComponent {
    fun injectBaseViewModel(baseViewModel: BaseViewModel)
    fun injectAuthTokenInterceptor(authTokenInterceptor: AuthTokenInterceptor)
    fun injectHttpCodeInterceptor(httpCodeInterceptor: HttpCodeInterceptor)
    fun injectAPI(api: API)
}