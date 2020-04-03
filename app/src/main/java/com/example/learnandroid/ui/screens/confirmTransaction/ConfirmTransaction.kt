package com.example.learnandroid.ui.screens.confirmTransaction

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.learnandroid.R
import com.example.learnandroid.ui.screens.login.LoginViewModel
import com.example.learnandroid.ui.utils.baseui.BaseFragment
import kotlinx.android.synthetic.main.confirm_transaction_fragment.*
import kotlinx.android.synthetic.main.login_fragment.*

class ConfirmTransaction : BaseFragment() {

    companion object {
        fun newInstance() = ConfirmTransaction()
    }

    private val confirmTransactionViewModel: ConfirmTransactionViewModel
        get() { return viewModel as ConfirmTransactionViewModel }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.confirm_transaction_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(ConfirmTransactionViewModel::class.java)

        super.onActivityCreated(savedInstanceState)

        setRegistrationButtonAction()
    }

    private fun setRegistrationButtonAction() {
        confirmTransactionButton?.setOnClickListener {
            confirmTransactionViewModel.createTransaction(amountField.text.toString())
        }
    }
}
