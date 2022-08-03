package com.spoton.kdsarchitecturesample.sample.presentation.feature.local.model

internal sealed class LocalUsersEffect {

    object NavigateBack : LocalUsersEffect()

    data class ShowUserId(val userId: String): LocalUsersEffect()
}
