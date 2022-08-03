package com.spoton.kdsarchitecturesample.sample.presentation.feature.local.binder

import com.spoton.kdsarchitecturesample.sample.presentation.common.adapter.UserAdapter
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.model.LocalUsersViewState

internal class LocalUsersBinder(
    private val adapter: UserAdapter,
) {

    fun bind(viewState: LocalUsersViewState) = with(viewState) {
        adapter.submitList(users)
    }
}