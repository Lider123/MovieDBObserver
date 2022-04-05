package ru.babaets.moviedbobserver.common.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDirections

interface AppNavigator {
    var controller: NavController?

    fun forward(action: NavDirections)

    fun replaceWith(vararg actions: NavDirections)

    fun replaceWith(action: NavDirections)

    fun newStack(action: NavDirections)

    fun back()
}
