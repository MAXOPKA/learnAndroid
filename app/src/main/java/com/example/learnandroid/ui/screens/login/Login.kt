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
import androidx.navigation.fragment.findNavController

import com.example.learnandroid.R
import kotlinx.android.synthetic.main.login_fragment.*

class Login : Fragment() {

    companion object {
        fun newInstance() = Login()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel

        setLoginButtonAction()
        setRegistrationButtonAction()
        initLiveData()
    }

    private fun setRegistrationButtonAction() {
        val navController = findNavController()
        val button = view?.findViewById<Button>(R.id.registrationButton)

        button?.setOnClickListener {
            navController.navigate(R.id.registration)
        }
    }

    private fun setLoginButtonAction() {
        val button = view?.findViewById<Button>(R.id.loginButton)

        button?.setOnClickListener {
            val emailField = view?.findViewById(R.id.emailField) as EditText
            val passwordField = view?.findViewById(R.id.passwordField) as EditText

            viewModel.login(emailField.text.toString(), passwordField.text.toString())
        }
    }

    private fun initLiveData() {
        initErrorTextLiveData()
        initSuccessTextLiveData()
        initOverlayLiveData()
    }

    private fun initErrorTextLiveData() {
        val textObserver = Observer<String?> { text ->
            errorText.text = text
            if (text.isNullOrEmpty()) {
                errorText.visibility = View.GONE
            } else {
                errorText.visibility = View.VISIBLE
            }
        }

        viewModel.textError.observe(this, textObserver)
    }

    private fun initSuccessTextLiveData() {
        val textObserver = Observer<String?> { text ->
            successText.text = text
            if (text.isNullOrEmpty()) {
                successText.visibility = View.GONE
            } else {
                successText.visibility = View.VISIBLE
            }
        }

        viewModel.textSuccess.observe(this, textObserver)
    }

    private fun initOverlayLiveData() {
        val overlayObserver = Observer<Boolean> { isLoading ->
            if (isLoading) {
                progressOverlay.visibility = View.VISIBLE
            } else {
                progressOverlay.visibility = View.GONE
            }
        }

        viewModel.isLoading.observe(this, overlayObserver)
    }
}
