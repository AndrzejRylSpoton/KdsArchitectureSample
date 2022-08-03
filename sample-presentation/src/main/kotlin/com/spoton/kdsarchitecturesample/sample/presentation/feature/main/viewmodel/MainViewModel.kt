package com.spoton.kdsarchitecturesample.sample.presentation.feature.main.viewmodel

import androidx.lifecycle.ViewModel
import com.spoton.kdsarchitecturesample.common.util.dispatcher.launchInMain
import com.spoton.kdsarchitecturesample.sample.presentation.feature.main.mapper.MainMapper
import com.spoton.kdsarchitecturesample.sample.presentation.feature.main.model.MainEffect
import com.spoton.kdsarchitecturesample.sample.presentation.feature.main.model.MainState
import com.spoton.kdsarchitecturesample.sample.presentation.feature.main.model.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject


@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val mapper: MainMapper,
) : ViewModel() {

    private val state = MutableStateFlow(MainState())
    val currentState: MainState get() = state.value
    val viewState: Flow<MainViewState> = state.map(mapper::from)

    private val _effect = Channel<MainEffect>()
    val effect: Flow<MainEffect> = _effect.receiveAsFlow()

    fun onDatabaseUsersClicked() {
        launchInMain {
            _effect.send(MainEffect.NavigateToDatabaseUsers)
        }
    }

    fun onNetworkUsersClicked() {
        launchInMain {
            _effect.send(MainEffect.NavigateToNetworkUsers)
        }
    }
}