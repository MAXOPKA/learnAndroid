package com.example.learnandroid.ui.components.userinfo

import android.opengl.Visibility
import com.example.learnandroid.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.learnandroid.ui.utils.baseui.BaseFragment
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.user_info_fragment.*

class UserInfo : BaseFragment() {
    companion object {
        fun newInstance() = UserInfo()
    }

    private val userInfoViewModel: UserInfoViewModel
        get() { return viewModel as UserInfoViewModel }

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
        setLogoutButtonAction()
        userInfoViewModel.getUserInfo()
    }

    private fun initLiveData() {
        val dataObserver = Observer<UserInfoLiveDataModel?> { dataModel ->
            setData(dataModel!!)
        }

        userInfoViewModel.getUserInfoData()?.observe(viewLifecycleOwner, dataObserver)
    }

    private fun setData(dataModel: UserInfoLiveDataModel) {
        if(dataModel.error || dataModel.balance == null) {
            userInfoContent.visibility = GONE

            return
        }

        userInfoContent.visibility = VISIBLE
        nameText.text = dataModel.name
        balanceText.text = "${dataModel.balance} PW"
    }

    private fun setLogoutButtonAction() {
        logoutButton?.setOnClickListener {
            userInfoViewModel.logout()
        }
    }
}
