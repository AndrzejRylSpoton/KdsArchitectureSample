package com.spoton.kdsarchitecturesample.sample.presentation.feature.network.model

import com.spoton.kdsarchitecturesample.sample.domain.model.UserDomainModel

internal data class NetworkUsersState(
    val users: List<UserDomainModel> = listOf(),
)
