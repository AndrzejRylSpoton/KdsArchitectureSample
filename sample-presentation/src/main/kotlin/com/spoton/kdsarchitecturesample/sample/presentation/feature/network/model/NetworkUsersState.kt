package com.spoton.kdsarchitecturesample.sample.presentation.feature.network.model

import com.spoton.kdsarchitecturesample.sample.domain.model.User

internal data class NetworkUsersState(
    val users: List<User> = listOf(),
)
