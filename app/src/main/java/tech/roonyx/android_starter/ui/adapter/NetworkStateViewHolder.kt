package tech.roonyx.android_starter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tech.roonyx.android_starter.common.NetworkState
import tech.roonyx.android_starter.databinding.ItemNetworkStateBinding
import tech.roonyx.android_starter.extension.getMessageUI

class NetworkStateViewHolder private constructor(
    private val binding: ItemNetworkStateBinding,
    private val retryCallback: (() -> Unit)
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(networkState: NetworkState, singleItem: Boolean = false) {
        if (singleItem) setMatchParent() else setWrapContent()

        when (networkState) {
            NetworkState.Success -> {
            }
            NetworkState.Loading -> loading()
            is NetworkState.Error -> failed(itemView.context, networkState)
        }
    }

    private fun setMatchParent() {
        itemView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    private fun setWrapContent() {
        itemView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun loading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnRetry.visibility = View.GONE
        binding.tvMessage.visibility = View.GONE
    }

    private fun failed(context: Context, state: NetworkState.Error) {
        binding.progressBar.visibility = View.GONE
        binding.btnRetry.visibility = View.VISIBLE
        binding.btnRetry.setOnClickListener { retryCallback() }
        binding.tvMessage.visibility = View.VISIBLE
        binding.tvMessage.text = state.exception.getMessageUI(context)
    }

    companion object {
        fun create(
            parent: ViewGroup,
            retryCallback: (() -> Unit)
        ) = NetworkStateViewHolder(
            ItemNetworkStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ),
            retryCallback
        )
    }
}