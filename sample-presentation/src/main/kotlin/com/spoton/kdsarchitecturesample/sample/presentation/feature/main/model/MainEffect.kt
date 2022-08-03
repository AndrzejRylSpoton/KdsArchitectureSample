package com.spoton.kdsarchitecturesample.sample.presentation.feature.main.model

internal sealed class MainEffect {

    object NavigateToDatabaseUsers : MainEffect()
    object NavigateToNetworkUsers : MainEffect()
}
