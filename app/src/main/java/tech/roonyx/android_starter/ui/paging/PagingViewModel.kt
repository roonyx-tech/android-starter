package tech.roonyx.android_starter.ui.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import tech.roonyx.android_starter.common.NetworkState
import tech.roonyx.android_starter.repository.Repository

/**
 * Example of using Paging
 */
class PagingViewModel(repository: Repository) : ViewModel() {

    private val listing = repository.getMessageListing(viewModelScope, PAGE_SIZE)

    val pagedListLiveData: LiveData<PagedList<String>> get() = listing.pagedList
    val networkStateLiveData = MediatorLiveData<NetworkState>().apply {
        addSource(listing.initialLoad) {
            value = it
        }
        addSource(listing.networkState) {
            value = it
        }
    }

    fun retry() {
        listing.retry?.invoke()
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}