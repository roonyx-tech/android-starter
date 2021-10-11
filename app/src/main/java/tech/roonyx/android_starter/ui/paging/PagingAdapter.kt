package tech.roonyx.android_starter.ui.paging

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import tech.roonyx.android_starter.ui.adapter.NetworkStateAdapter

/**
 * Example of using Paging
 */
class PagingAdapter(
    retryCallback: () -> Unit
) : NetworkStateAdapter<String>(
    retryCallback, DIFF_UTIL
) {

    override fun onCreateItemViewHolder(parent: ViewGroup): ItemViewHolder {
        return PagingViewHolder.create(parent)
    }

    override fun onBindItemViewHolder(holder: ItemViewHolder, item: String) {
        if (holder is PagingViewHolder) holder.bind(item)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}