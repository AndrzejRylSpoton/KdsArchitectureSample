package com.spoton.kdsarchitecturesample.common.resourceprovider

import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes resId: Int, vararg args: Any): String
}