package com.example.learnandroid.ui.components.userinfo

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.learnandroid.R
import com.example.learnandroid.ui.screens.login.LoginLiveDataModel
import com.example.learnandroid.ui.screens.registration.RegistrationLiveDataModel
import com.example.learnandroid.ui.screens.transactionsList.TransactionsListViewModel
import com.example.learnandroid.ui.utils.baseui.BaseFragment
import kotlinx.android.synthetic.main.user_info_fragment.*

class UserInfo : BaseFragment() {

    companion object {
        fun newInstance() = UserInfo()
    }

    private val userInfoViewModel: UserInfoViewModel
        get() { return viewModel as UserInfoViewModel
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(UserInfoViewModel::class.java)

        super.onActivityCreated(savedInstanceState)
        initLiveData()

        userInfoViewModel.getUserInfo()
    }

    private fun initLiveData() {
        val dataObserver = Observer<UserInfoLiveDataModel?> { dataModel ->
            setData(dataModel!!)
        }

        userInfoViewModel.getUserInfoData()?.observe(viewLifecycleOwner, dataObserver)
    }

    private fun setData(dataModel: UserInfoLiveDataModel) {
        if (dataModel.error) {
            view?.visibility = View.GONE
        } else {
            view?.visibility = View.VISIBLE
        }

        nameText.text = dataModel.name
        balanceText.text = dataModel.balance
    }
}
