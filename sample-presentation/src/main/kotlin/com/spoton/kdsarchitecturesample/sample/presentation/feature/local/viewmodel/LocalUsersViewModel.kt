package com.spoton.kdsarchitecturesample.sample.presentation.feature.local.viewmodel

import androidx.lifecycle.ViewModel
import com.spoton.kdsarchitecturesample.sample.domain.model.User
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.mapper.LocalUsersMapper
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.model.LocalUsersEffect
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.model.LocalUsersState
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.model.LocalUsersViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
internal class LocalUsersViewModel @Inject constructor(
    mapper: LocalUsersMapper,
) : ViewModel() {

    private val state = MutableStateFlow(
        LocalUsersState(
            users = listOf(
                User(id = "id1", firstName = "user", secondName = "1"),
                User(id = "id2", firstName = "user", secondName = "2"),
            )
        )
    )
    val currentState: LocalUsersState get() = state.value
    val viewState: Flow<LocalUsersViewState> = state.map(mapper::from)

    private val _effect = Channel<LocalUsersEffect>()
    val effect: Flow<LocalUsersEffect> = _effect.receiveAsFlow()

    fun onUserClicked(userId: String) {
        TODO("Not yet implemented")
    }
}