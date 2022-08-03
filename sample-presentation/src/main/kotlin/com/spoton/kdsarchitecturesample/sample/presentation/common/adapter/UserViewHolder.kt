package com.spoton.kdsarchitecturesample.sample.presentation.common.adapter

import android.view.ViewGroup
import com.spoton.kdsarchitecturesample.common.ui.BindingViewHolder
import com.spoton.kdsarchitecturesample.sample.presentation.common.model.UserUIModel
import com.spoton.kdsarchitecturesample.sample.presentation.databinding.ItemUserBinding

class UserViewHolder(
    parent: ViewGroup,
    private val onUserClickedListener: ((UserUIModel) -> Unit)?,
) : BindingViewHolder<UserUIModel, ItemUserBinding>(
    ItemUserBinding::inflate,
    parent
) {
    override fun ItemUserBinding.bind(item: UserUIModel) {
        itemUserUsernameTextView.text = item.displayName
        root.setOnClickListener { onUserClickedListener?.invoke(item) }
    }
}
