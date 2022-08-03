package com.spoton.kdsarchitecturesample.sample.presentation.feature.local.router

import androidx.navigation.NavController

internal class LocalUsersRouterImpl(
    private val navController: NavController
) : LocalUsersRouter {

    override fun navigateUp() {
        navController.navigateUp()
    }
}