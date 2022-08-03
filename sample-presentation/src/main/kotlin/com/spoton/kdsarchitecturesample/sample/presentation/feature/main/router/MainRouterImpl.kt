package com.spoton.kdsarchitecturesample.sample.presentation.feature.main.router

import androidx.navigation.NavController
import com.spoton.kdsarchitecturesample.sample.presentation.feature.main.MainFragmentDirections

internal class MainRouterImpl(
    private val navController: NavController,
) : MainRouter {

    override fun navigateToDatabaseUsers() {
        navController.navigate(MainFragmentDirections.navigateToLocalUsers())
    }

    override fun navigateToNetworkUsers() {
        navController.navigate(MainFragmentDirections.navigateToNetworkUsers())
    }
}