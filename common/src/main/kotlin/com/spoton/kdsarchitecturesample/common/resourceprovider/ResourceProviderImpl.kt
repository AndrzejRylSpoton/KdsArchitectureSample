package com.spoton.kdsarchitecturesample.common.resourceprovider

import android.content.Context

class ResourceProviderImpl(
    private val context: Context
) : ResourceProvider {


    override fun getString(resId: Int, vararg args: Any): String =
        context.getString(resId, *args)
}