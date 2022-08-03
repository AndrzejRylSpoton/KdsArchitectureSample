package com.spoton.kdsarchitecturesample.sample.presentation.feature.network.router

import androidx.navigation.NavController
import com.spoton.kdsarchitecturesample.sample.presentation.feature.network.router.NetworkUsersRouter

internal class NetworkUsersRouterImpl(
    private val navController: NavController
) : NetworkUsersRouter {

    override fun navigateUp() {
        navController.navigateUp()
    }
}