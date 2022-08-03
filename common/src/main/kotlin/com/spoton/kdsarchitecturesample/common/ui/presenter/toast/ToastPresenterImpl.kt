package com.spoton.kdsarchitecturesample.common.ui.presenter.toast

import android.content.Context
import android.widget.Toast
import javax.inject.Inject

internal class ToastPresenterImpl @Inject constructor(
    private val context: Context
) : ToastPresenter {

    override fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}