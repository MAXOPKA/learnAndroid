package com.example.learnandroid.ui.screens.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.learnandroid.R
import com.example.learnandroid.ui.screens.login.LoginViewModel
import com.example.learnandroid.ui.utils.MessageTypes
import com.example.learnandroid.ui.utils.baseui.BaseFragment
import kotlinx.android.synthetic.main.registration_fragment.messageText

class Registration : BaseFragment() {

    companion object {
        fun newInstance() = Registration()
    }

    private val registrationViewModel: RegistrationViewModel
        get() { return viewModel as RegistrationViewModel }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registration_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(RegistrationViewModel::class.java)

        super.onActivityCreated(savedInstanceState)

        setRegistrationButtonAction()
        initLiveData()
    }

    private fun setRegistrationButtonAction() {
        val button = view?.findViewById<Button>(R.id.registrationButton)

        button?.setOnClickListener {
            val nameField = view?.findViewById(R.id.nameField) as EditText
            val emailField = view?.findViewById(R.id.emailField) as EditText
            val passwordField = view?.findViewById(R.id.passwordField) as EditText
            val passwordRetryField = view?.findViewById(R.id.passwordRetryField) as EditText

            registrationViewModel.registration(
                nameField.text.toString(),
                emailField.text.toString(),
                passwordField.text.toString(),
                passwordRetryField.text.toString())
        }
    }

    private fun initLiveData() {
        val dataObserver = Observer<RegistrationLiveDataModel?> { dataModel ->
            setData(dataModel!!)
        }

        registrationViewModel.liveDataModel.observe(viewLifecycleOwner, dataObserver)
    }

    fun setData(dataModel: RegistrationLiveDataModel) {
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
}
