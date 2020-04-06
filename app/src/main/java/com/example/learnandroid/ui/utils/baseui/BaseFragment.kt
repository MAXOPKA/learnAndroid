package com.example.learnandroid.ui.utils.baseui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.ui.screens.login.LoginViewModel
import com.example.learnandroid.ui.utils.navigation.NavigationCommand
import kotlinx.android.synthetic.main.loader.*

open class BaseFragment : Fragment() {
    open lateinit var viewModel: BaseViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState == null) {
//            viewModel?.setArguments(arguments)
//            viewModel?.loadData()
        }

        val navigationObserver = Observer<NavigationCommand?> { command ->
            when (command) {
                is NavigationCommand.To -> {
                    findNavController().navigate(command.directions, command.navOptions)
                }
            }
        }

        viewModel?.getNavigationCommands()?.observe(viewLifecycleOwner, navigationObserver)
    }

    fun setOverlayVisiblity(isLoading: Boolean) {
        if (isLoading) {
            progressOverlay.visibility = View.VISIBLE
        } else {
            progressOverlay.visibility = View.GONE
        }
    }

    fun checkAuthorization() {

    }
}
