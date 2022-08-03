package com.spoton.kdsarchitecturesample.sample.presentation.feature.main.mapper

import com.spoton.kdsarchitecturesample.common.resourceprovider.ResourceProvider
import com.spoton.kdsarchitecturesample.sample.presentation.R
import com.spoton.kdsarchitecturesample.sample.presentation.feature.main.model.MainState
import com.spoton.kdsarchitecturesample.sample.presentation.feature.main.model.MainViewState
import javax.inject.Inject

internal class MainMapper @Inject constructor(
    private val resourceProvider: ResourceProvider,
) {

    fun from(state: MainState): MainViewState = with(state) {
        MainViewState(
            databaseUsersButtonText = resourceProvider.getString(
                R.string.database_source_button,
                databaseUsersCount
            )
        )
    }
}