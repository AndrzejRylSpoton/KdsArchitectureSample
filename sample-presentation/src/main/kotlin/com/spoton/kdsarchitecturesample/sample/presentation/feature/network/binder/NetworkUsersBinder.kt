package com.spoton.kdsarchitecturesample.sample.presentation.feature.network.binder

import com.spoton.kdsarchitecturesample.sample.presentation.common.adapter.UserAdapter
import com.spoton.kdsarchitecturesample.sample.presentation.feature.network.model.NetworkUsersViewState

internal class NetworkUsersBinder(
    private val adapter: UserAdapter,
) {

    fun bind(viewState: NetworkUsersViewState) = with(viewState) {
        adapter.submitList(users)
    }
}