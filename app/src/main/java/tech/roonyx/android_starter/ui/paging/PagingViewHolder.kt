package tech.roonyx.android_starter.ui.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import tech.roonyx.android_starter.databinding.ItemPagingBinding
import tech.roonyx.android_starter.ui.adapter.NetworkStateAdapter

/**
 * Example of using Paging
 */
class PagingViewHolder private constructor(
    private val binding: ItemPagingBinding
) : NetworkStateAdapter.ItemViewHolder(binding.root) {

    fun bind(item: String) {
        binding.tvMessage.text = item
    }

    companion object {
        fun create(parent: ViewGroup) = PagingViewHolder(
            ItemPagingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }
}