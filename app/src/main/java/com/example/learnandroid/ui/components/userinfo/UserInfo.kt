package com.example.learnandroid.ui.components.userinfo

import com.example.learnandroid.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.learnandroid.databinding.UserInfoFragmentBinding
import com.example.learnandroid.services.api.responses.UserInfoToken
import com.example.learnandroid.ui.utils.baseui.BaseFragment
import kotlinx.android.synthetic.main.user_info_fragment.*

class UserInfo : BaseFragment() {

    lateinit var binding: UserInfoFragmentBinding

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
        binding = DataBindingUtil.inflate(inflater, R.layout.user_info_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(UserInfoViewModel::class.java)

        super.onActivityCreated(savedInstanceState)
        // initLiveData()
        binding.userInfoModel = userInfoViewModel.userInfo
        userInfoViewModel.getUserInfo()
    }

    private fun initLiveData() {
        val dataObserver = Observer<UserInfoLiveDataModel?> { dataModel ->
            setData(dataModel!!)
        }

        userInfoViewModel.getUserInfoData()?.observe(viewLifecycleOwner, dataObserver)
    }

    private fun setData(dataModel: UserInfoLiveDataModel) {

    }

    public fun onClickLogout() {
        userInfoViewModel.logout()
    }
}
