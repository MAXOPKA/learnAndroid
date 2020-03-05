package com.example.learnandroid.ui.components.usersList

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.learnandroid.R
import com.example.learnandroid.ui.screens.selectUser.SelectUserViewModel
import com.example.learnandroid.ui.utils.baseui.BaseFragment

class UsersList : BaseFragment() {

    companion object {
        fun newInstance() = UsersList()
    }

    val usersListViewModel: UsersListViewModel
        get() { return viewModel as UsersListViewModel }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.users_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(UsersListViewModel::class.java)
        super.onActivityCreated(savedInstanceState)
    }

}
