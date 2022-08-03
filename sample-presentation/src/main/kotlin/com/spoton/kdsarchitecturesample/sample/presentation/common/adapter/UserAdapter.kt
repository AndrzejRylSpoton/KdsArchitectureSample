package com.spoton.kdsarchitecturesample.sample.presentation.common.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.spoton.kdsarchitecturesample.common.ui.ItemDiffer
import com.spoton.kdsarchitecturesample.sample.presentation.common.model.UserUIModel

class UserAdapter : ListAdapter<UserUIModel, UserViewHolder>(ItemDiffer(UserUIModel::id)) {

    private lateinit var listener: (UserUIModel) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(parent, listener)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnUserClickedListener(listener: (UserUIModel) -> Unit) {
        this.listener = listener
    }
}