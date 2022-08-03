package com.spoton.kdsarchitecturesample.sample.presentation.feature.local.viewmodel

import androidx.lifecycle.ViewModel
import com.spoton.kdsarchitecturesample.common.util.dispatcher.launchInBackground
import com.spoton.kdsarchitecturesample.common.util.dispatcher.launchInMain
import com.spoton.kdsarchitecturesample.common.util.dispatcher.onMain
import com.spoton.kdsarchitecturesample.sample.domain.interactor.ObserveLocalUsersInteractor
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.mapper.LocalUsersMapper
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.model.LocalUsersEffect
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.model.LocalUsersState
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.model.LocalUsersViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
internal class LocalUsersViewModel @Inject constructor(
    mapper: LocalUsersMapper,
    private val observeLocalUsersInteractor: ObserveLocalUsersInteractor,
) : ViewModel() {

    private val state = MutableStateFlow(LocalUsersState())
    val currentState: LocalUsersState get() = state.value
    val viewState: Flow<LocalUsersViewState> = state.map(mapper::from)

    private val _effect = Channel<LocalUsersEffect>()
    val effect: Flow<LocalUsersEffect> = _effect.receiveAsFlow()

    init {
        launchInBackground {
            observeLocalUsersInteractor.run()
                .onEach {
                    onMain {
                        state.value = currentState.copy(
                            users = it
                        )
                    }
                }
                .launchIn(this)
        }
    }

    fun onUserClicked(userId: String) {
        launchInMain {
            _effect.send(LocalUsersEffect.ShowUserId(userId))
        }
    }
}