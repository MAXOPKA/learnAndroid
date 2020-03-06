package com.example.learnandroid.ui.screens.selectUser

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.learnandroid.R
import com.example.learnandroid.ui.components.searchbar.SearchBar
import com.example.learnandroid.ui.components.usersList.UsersList
import com.example.learnandroid.ui.screens.transactionsList.TransactionsListLiveDataModel
import com.example.learnandroid.ui.screens.transactionsList.TransactionsListViewModel
import com.example.learnandroid.ui.utils.baseui.BaseFragment
import kotlinx.android.synthetic.main.select_user_fragment.*

class SelectUser : BaseFragment() {

    companion object {
        fun newInstance() = SelectUser()
    }

    private val selectUserViewModel: SelectUserViewModel
        get() { return viewModel as SelectUserViewModel
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.select_user_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(SelectUserViewModel::class.java)

        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        val searchFragment = childFragmentManager.findFragmentById(R.id.searchBarFragment)
        val usersListFragment = childFragmentManager.findFragmentById(R.id.usersListFragment)

        (usersListFragment as UsersList).usersListViewModel.input = (searchFragment as SearchBar).searchBarViewModel.output
    }

}
