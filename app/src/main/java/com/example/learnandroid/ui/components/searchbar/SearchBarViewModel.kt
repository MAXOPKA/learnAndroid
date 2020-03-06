package com.example.learnandroid.ui.components.searchbar

import com.example.learnandroid.ui.utils.baseui.BaseViewModel
import io.reactivex.Emitter
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class SearchBarViewModel : BaseViewModel() {
    private var emitter: Emitter<String>? = null

    val output: Observable<String> = Observable.create<String> { emitter ->
        this.emitter = emitter
    }.debounce(1L, TimeUnit.SECONDS)

    fun postKey(searchKey: String) {
        emitter?.onNext(searchKey)
    }
}
