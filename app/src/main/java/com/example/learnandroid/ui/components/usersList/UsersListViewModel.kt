package com.example.learnandroid.ui.components.usersList

import androidx.lifecycle.MutableLiveData
import com.example.learnandroid.models.UserModel
import com.example.learnandroid.models.UsersModel
import com.example.learnandroid.services.IAPI
import com.example.learnandroid.services.IPreferences
import com.example.learnandroid.ui.screens.login.LoginDirections
import com.example.learnandroid.ui.screens.selectUser.SelectUserDirections
import com.example.learnandroid.ui.utils.baseui.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class UsersListViewModel() : BaseViewModel() {
    var liveDataModel = MutableLiveData<UsersListLiveDataModel>(
        UsersListLiveDataModel(emptyList(), false, false)
    )

    var input: Observable<String>? = null
        set(value) {
            value
                ?.debounce(1L, TimeUnit.SECONDS)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe {
                    getUsers(it)
                }
        }

    init {
        apiService.usersListOutput
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                usersHandler(result)
            }, { error ->
                usersErrorHandler(error)
            })
    }

    fun clickOnUser(user: UserModel) {
        navigate(SelectUserDirections.actionSelectUserToConfirmTransaction(user.id, user.name))
    }

    private fun getUsers(key: String) {
        if(key.length == 0) {
            liveDataModel.value = liveDataModel.value?.apply {
                users = emptyList()
            }

            return
        }


        liveDataModel.value = liveDataModel.value?.apply { isLoading = true }

        apiService.usersList(key)
    }

    private fun usersErrorHandler(error: Throwable?) {
        liveDataModel.value = liveDataModel.value?.apply {
            isLoading = false
            users = emptyList()
        }
    }

    private fun usersHandler(result: UsersModel?) {
        liveDataModel.value = liveDataModel.value?.apply {
            users = result?.usersList ?: emptyList()
            isLoading = false
        }
    }
}
