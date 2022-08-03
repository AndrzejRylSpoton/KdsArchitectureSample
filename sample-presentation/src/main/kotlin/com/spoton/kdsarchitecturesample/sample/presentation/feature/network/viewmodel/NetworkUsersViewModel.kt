package com.spoton.kdsarchitecturesample.sample.presentation.feature.network.viewmodel

import androidx.lifecycle.ViewModel
import com.spoton.kdsarchitecturesample.common.util.dispatcher.launchInMain
import com.spoton.kdsarchitecturesample.sample.domain.model.User
import com.spoton.kdsarchitecturesample.sample.presentation.feature.network.mapper.NetworkUsersMapper
import com.spoton.kdsarchitecturesample.sample.presentation.feature.network.model.NetworkUsersEffect
import com.spoton.kdsarchitecturesample.sample.presentation.feature.network.model.NetworkUsersState
import com.spoton.kdsarchitecturesample.sample.presentation.feature.network.model.NetworkUsersViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
internal class NetworkUsersViewModel @Inject constructor(
    mapper: NetworkUsersMapper,
) : ViewModel() {

    private val state = MutableStateFlow(
        NetworkUsersState(
            users = listOf(
                User(id = "id1", firstName = "user", secondName = "1"),
                User(id = "id2", firstName = "user", secondName = "2"),
            )
        )
    )
    val currentState: NetworkUsersState get() = state.value
    val viewState: Flow<NetworkUsersViewState> = state.map(mapper::from)

    private val _effect = Channel<NetworkUsersEffect>()
    val effect: Flow<NetworkUsersEffect> = _effect.receiveAsFlow()

    fun onUserClicked(userId: String) {

    }
}