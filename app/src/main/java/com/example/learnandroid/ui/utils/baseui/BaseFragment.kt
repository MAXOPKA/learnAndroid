package com.example.learnandroid.ui.utils.baseui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.ui.screens.login.LoginViewModel
import com.example.learnandroid.ui.utils.navigation.NavigationCommand

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
                is NavigationCommand.To ->
                    findNavController().navigate(command.directions)
            }
        }

        viewModel?.getNavigationCommands()?.observe(viewLifecycleOwner, navigationObserver)
    }
}
