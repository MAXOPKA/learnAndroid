package com.example.learnandroid.ui.components.searchbar

import com.example.learnandroid.ui.utils.baseui.BaseViewModel
import io.reactivex.Observable

class SearchBarViewModel : BaseViewModel() {
    val key: String = ""

    val output: Observable<String> = Observable.create<String> { emitter ->
        postKey() {
            emitter.onNext(it)
        }
    }

    fun postKey(emit: (key: String) -> Unit) {
        emit(key)
    }
}
