package com.spoton.kdsarchitecturesample.sample.presentation.feature.network.viewmodel

import androidx.lifecycle.ViewModel
import com.spoton.kdsarchitecturesample.common.util.dispatcher.launchInBackground
import com.spoton.kdsarchitecturesample.common.util.dispatcher.launchInMain
import com.spoton.kdsarchitecturesample.common.util.dispatcher.onMain
import com.spoton.kdsarchitecturesample.common.util.dispatcher.sendOnMain
import com.spoton.kdsarchitecturesample.sample.domain.interactor.GetNetworkUsersInteractor
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
    private val getNetworkUsersInteractor: GetNetworkUsersInteractor,
) : ViewModel() {

    private val state = MutableStateFlow(NetworkUsersState())
    val currentState: NetworkUsersState get() = state.value
    val viewState: Flow<NetworkUsersViewState> = state.map(mapper::from)

    private val _effect = Channel<NetworkUsersEffect>()
    val effect: Flow<NetworkUsersEffect> = _effect.receiveAsFlow()

    init {
        launchInBackground {
            getNetworkUsersInteractor.run()
                .onSuccess {
                    onMain {
                        state.value = currentState.copy(
                            users = it
                        )
                    }
                }
                .onFailure {
                    launchInMain {
                        _effect.sendOnMain(NetworkUsersEffect.ShowError(it.message))
                    }
                }
        }
    }

    fun onUserClicked(userId: String) {
        launchInMain {
            _effect.sendOnMain(NetworkUsersEffect.ShowUserId(userId))
        }
    }
}