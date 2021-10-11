package tech.roonyx.android_starter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tech.roonyx.android_starter.common.NetworkState

abstract class NetworkStateAdapter<T>(
    private val retryCallback: () -> Unit,
    diffCallback: DiffUtil.ItemCallback<T>
) : PagedListAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

    var networkState: NetworkState = NetworkState.Loading
        set(newNetworkState) {
            val prevState = field
            val hadNetworkStateItem = hasNetworkStateItem
            field = newNetworkState

            if (hadNetworkStateItem != hasNetworkStateItem) {
                if (hadNetworkStateItem) notifyItemRemoved(super.getItemCount())
                else notifyItemInserted(super.getItemCount())
            } else if (hasNetworkStateItem && prevState != newNetworkState) {
                notifyItemChanged(itemCount - 1)
            }
        }

    /**
     * If true - NetworkItem sets MATCH_PARENT height
     */
    open val isNetworkItemSingle: Boolean get() = itemCount == 1

    private val hasNetworkStateItem
        get() = networkState != NetworkState.Success

    abstract fun onCreateItemViewHolder(parent: ViewGroup): ItemViewHolder

    abstract fun onBindItemViewHolder(holder: ItemViewHolder, item: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ViewType.values()[viewType]) {
            ViewType.ITEM -> onCreateItemViewHolder(parent)
            ViewType.NETWORK_STATE -> NetworkStateViewHolder.create(parent, retryCallback)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> getItem(position)?.let { onBindItemViewHolder(holder, it) }
            is NetworkStateViewHolder -> holder.bind(networkState, isNetworkItemSingle)
        }
    }

    override fun getItemViewType(position: Int): Int = when {
        hasNetworkStateItem && position == itemCount - 1 -> ViewType.NETWORK_STATE.ordinal
        else -> ViewType.ITEM.ordinal
    }

    override fun getItemCount(): Int = super.getItemCount() +
            if (hasNetworkStateItem) 1 else 0

    open class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        constructor(@LayoutRes layout: Int, parent: ViewGroup) : this(
            LayoutInflater.from(parent.context).inflate(
                layout, parent, false
            )
        )
    }

    private enum class ViewType {
        ITEM, NETWORK_STATE
    }
}