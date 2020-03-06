package com.example.learnandroid.ui.components.userinfo

import com.example.learnandroid.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
        val userInfoView = inflater.inflate(R.layout.select_user_fragment, container, false)
       // val binding: UserInfoFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.select_user_fragment, container, false)
//        binding.setViewModel(userInfoViewModel.userInfo)
        return userInfoView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(UserInfoViewModel::class.java)

        super.onActivityCreated(savedInstanceState)
        // initLiveData()

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
