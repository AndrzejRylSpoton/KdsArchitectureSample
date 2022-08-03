package com.spoton.kdsarchitecturesample.sample.presentation.feature.network.model

internal sealed class NetworkUsersEffect {

    object NavigateBack : NetworkUsersEffect()

    data class ShowUserId(val userId: String) : NetworkUsersEffect()

    data class ShowError(val errorMessage: String?) : NetworkUsersEffect()
}
