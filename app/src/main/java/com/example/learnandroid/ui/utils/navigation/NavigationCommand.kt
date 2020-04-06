package com.example.learnandroid.ui.utils.navigation

import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

sealed class NavigationCommand {
    data class To(val directions: NavDirections, val navOptions: NavOptions? = null, val clear: Boolean = false): NavigationCommand()
    object Back: NavigationCommand()
    data class BackTo(val destinationId: Int): NavigationCommand()
    object ToRoot: NavigationCommand()
    object ToLogin: NavigationCommand()
}