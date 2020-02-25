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
import kotlinx.android.synthetic.main.registration_fragment.*

class Registration : Fragment() {

    companion object {
        fun newInstance() = Registration()
    }

    private lateinit var viewModel: RegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registration_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegistrationViewModel::class.java)
        // TODO: Use the ViewModel
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

            viewModel.registration(
                nameField.text.toString(),
                emailField.text.toString(),
                passwordField.text.toString(),
                passwordRetryField.text.toString())
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
