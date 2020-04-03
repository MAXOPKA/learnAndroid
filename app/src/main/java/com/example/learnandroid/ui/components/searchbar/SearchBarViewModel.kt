package com.example.learnandroid.ui.components.searchbar

import com.example.learnandroid.services.IAPI
import com.example.learnandroid.services.IPreferences
import com.example.learnandroid.ui.utils.baseui.BaseViewModel
import io.reactivex.Emitter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchBarViewModel() : BaseViewModel() {
    val output: PublishSubject<String> = PublishSubject.create()

    fun postKey(searchKey: String) {
        output.onNext(searchKey)
    }
}
