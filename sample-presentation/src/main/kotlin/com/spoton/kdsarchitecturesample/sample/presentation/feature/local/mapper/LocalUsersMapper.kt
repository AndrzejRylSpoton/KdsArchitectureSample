package com.spoton.kdsarchitecturesample.sample.presentation.feature.local.mapper

import com.spoton.kdsarchitecturesample.sample.presentation.common.model.UserUIModel
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.model.LocalUsersState
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.model.LocalUsersViewState
import javax.inject.Inject

internal class LocalUsersMapper @Inject constructor() {

    fun from(state: LocalUsersState): LocalUsersViewState = with(state) {
        LocalUsersViewState(
            users = users.map {
                UserUIModel(
                    id = it.id,
                    displayName = "${it.name}: ${it.status}"
                )
            }
        )
    }
}