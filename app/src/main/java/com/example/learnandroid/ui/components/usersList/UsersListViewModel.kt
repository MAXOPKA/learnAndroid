package com.example.learnandroid.ui.components.usersList

import androidx.lifecycle.ViewModel
import com.example.learnandroid.models.UsersModel
import com.example.learnandroid.ui.utils.baseui.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UsersListViewModel : BaseViewModel() {
    var input: Observable<String>? = null
        set(value) {
            value?.subscribe() {
                getUsers(it)
            }
        }

    private fun getUsers(key: String) {
        apiService.usersList(key)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                usersHandler(result)
            }, { error ->
                usersErrorHandler(error)
            })
    }

    private fun usersErrorHandler(error: Throwable?) {

    }

    private fun usersHandler(result: UsersModel?) {

    }
}
