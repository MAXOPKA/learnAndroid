package com.example.learnandroid.ui.utils.baseui

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.MainActivity
import com.example.learnandroid.R
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
            val navController = (activity as MainActivity).navController
            when (command) {
                is NavigationCommand.To -> {
                   navController?.navigate(command.directions, command.navOptions)
                }
                is NavigationCommand.ToLogin -> {
                    navController?.navigate(R.id.action_global_login)
                }
                is NavigationCommand.ToTransactionsList -> {
                    navController?.navigate(R.id.action_global_transactionsList)
                }
                is NavigationCommand.Back -> {
                    if (navController?.previousBackStackEntry != null) {
                        navController?.popBackStack()
                    }
                }
                is NavigationCommand.BackTo -> {
                    navController?.popBackStack(command.destinationId, true)
                }
            }
            //if (navController?.previousBackStackEntry == null) {
            //}
            val a = navController
        }

        val actionBar: ActionBar? = (activity as? AppCompatActivity)?.supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true)

        viewModel?.getNavigationCommands()?.observe(viewLifecycleOwner, navigationObserver)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            viewModel.navigateBack()
            return true;
        }
        return false;
    }

    fun setOverlayVisiblity(isLoading: Boolean) {
        if (isLoading) {
            progressOverlay.visibility = View.VISIBLE
        } else {
            progressOverlay.visibility = View.GONE
        }
    }

    open fun hideKeyboard(activity: FragmentActivity?) {
        val imm: InputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun checkAuthorization() {

    }
}
