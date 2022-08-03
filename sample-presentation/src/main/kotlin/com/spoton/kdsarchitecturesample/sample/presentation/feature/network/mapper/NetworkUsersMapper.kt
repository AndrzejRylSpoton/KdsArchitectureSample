package com.spoton.kdsarchitecturesample.sample.presentation.feature.network.mapper

import com.spoton.kdsarchitecturesample.sample.presentation.common.model.UserUIModel
import com.spoton.kdsarchitecturesample.sample.presentation.feature.network.model.NetworkUsersState
import com.spoton.kdsarchitecturesample.sample.presentation.feature.network.model.NetworkUsersViewState
import javax.inject.Inject

internal class NetworkUsersMapper @Inject constructor() {

    fun from(state: NetworkUsersState): NetworkUsersViewState = with(state) {
        NetworkUsersViewState(
            users = users.map {
                UserUIModel(
                    id = it.id,
                    name = "${it.firstName} ${it.secondName}"
                )
            }
        )
    }
}