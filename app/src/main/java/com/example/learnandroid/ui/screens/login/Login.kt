package com.example.learnandroid.ui.screens.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController

import com.example.learnandroid.R
import com.example.learnandroid.ui.utils.MessageTypes
import com.example.learnandroid.ui.utils.baseui.BaseFragment
import com.example.learnandroid.ui.utils.baseui.BaseViewModel
import kotlinx.android.synthetic.main.login_fragment.*

class Login : BaseFragment() {

    companion object {
        fun newInstance() = Login()
    }

    private val loginViewModel: LoginViewModel
        get() { return viewModel as LoginViewModel }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        super.onActivityCreated(savedInstanceState)

        setLoginButtonAction()
        setRegistrationButtonAction()
        initLiveData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    private fun setRegistrationButtonAction() {
        val navController = findNavController(this)
        val button = view?.findViewById<Button>(R.id.registrationButton)

        button?.setOnClickListener {
            loginViewModel.navigateToRegistration()
        }
    }

    private fun setLoginButtonAction() {
        val button = view?.findViewById<Button>(R.id.loginButton)

        button?.setOnClickListener {
            val emailField = view?.findViewById(R.id.emailField) as EditText
            val passwordField = view?.findViewById(R.id.passwordField) as EditText

            loginViewModel.login(emailField.text.toString(), passwordField.text.toString())
        }
    }

    private fun initLiveData() {
        val dataObserver = Observer<LoginDataModel?> { dataModel ->
            setData(dataModel!!)
        }

        loginViewModel.getLoginData()?.observe(viewLifecycleOwner, dataObserver)
    }

    fun setData(dataModel: LoginDataModel) {
        setMessageText(dataModel.messageText)
        setMessageColor(dataModel.messageType)
        setOverlayVisiblity(dataModel.isLoading)
    }

    private fun setMessageText(text: String?) {
        messageText.text = text

        if (text.isNullOrEmpty()) {
            messageText.visibility = View.GONE
        } else {
            messageText.visibility = View.VISIBLE
        }
    }

    private fun setMessageColor(messageType: MessageTypes) {
        messageText.setTextColor(messageType.rgb)
    }

    private fun setOverlayVisiblity(isLoading: Boolean) {
        if (isLoading) {
            progressOverlay.visibility = View.VISIBLE
        } else {
            progressOverlay.visibility = View.GONE
        }
    }
}
