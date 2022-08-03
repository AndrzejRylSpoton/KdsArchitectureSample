package com.spoton.kdsarchitecturesample.sample.presentation.feature.main.binder

import android.widget.Button
import com.spoton.kdsarchitecturesample.sample.presentation.feature.main.model.MainViewState

internal class MainBinder(
    private val databaseUsersButton: Button
) {

    fun bind(viewState: MainViewState) = with(viewState) {
        databaseUsersButton.text = databaseUsersButtonText
    }
}