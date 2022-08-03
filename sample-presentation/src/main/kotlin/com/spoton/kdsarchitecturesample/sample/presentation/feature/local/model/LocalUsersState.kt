package com.spoton.kdsarchitecturesample.sample.presentation.feature.local.model

import com.spoton.kdsarchitecturesample.sample.domain.model.User

internal data class LocalUsersState(
    val users: List<User> = listOf(),
)
