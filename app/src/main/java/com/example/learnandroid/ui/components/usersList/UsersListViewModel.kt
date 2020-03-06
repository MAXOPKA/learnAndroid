package com.example.learnandroid.ui.components.usersList

import androidx.lifecycle.MutableLiveData
import com.example.learnandroid.models.UsersModel
import com.example.learnandroid.ui.utils.baseui.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UsersListViewModel : BaseViewModel() {
    var liveDataModel = MutableLiveData<UsersListLiveDataModel>(
        UsersListLiveDataModel(emptyList(), false, false)
    )

    var input: Observable<String>? = null
        set(value) {
            value
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe {
                    getUsers(it)
                }
        }

    private fun getUsers(key: String) {
        liveDataModel.value = liveDataModel.value?.apply { isLoading = true }

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
        liveDataModel.value = liveDataModel.value?.apply { isLoading = false }
    }

    private fun usersHandler(result: UsersModel?) {
        liveDataModel.value = liveDataModel.value?.apply {
            users = result?.usersList ?: emptyList()
            isLoading = false
        }
    }
}
