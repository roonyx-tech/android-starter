package tech.roonyx.android_starter.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import tech.roonyx.android_starter.repository.dataSource.PositionalNetworkStateFactory

data class RetryListing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val initialLoad: LiveData<NetworkState>,
    val networkState: LiveData<NetworkState>,
    val refresh: (() -> Unit)? = null,
    val retry: (() -> Unit)? = null
)

fun <K, V> createPagedList(
    pageSize: Int,
    factory: DataSource.Factory<K, V>
): LiveData<PagedList<V>> = PagedList.Config.Builder()
    .setEnablePlaceholders(false)
    .setPageSize(pageSize)
    .setInitialLoadSizeHint(pageSize)
    .build()
    .let { factory.toLiveData(it) }

fun <T> PositionalNetworkStateFactory<T>.createRetryListing(
    pageSize: Int
): RetryListing<T> = RetryListing(
    createPagedList(pageSize, this),
    sourceLiveData.switchMap { it.initialStateLiveData },
    sourceLiveData.switchMap { it.loadingStateLiveData },
    refresh = { sourceLiveData.value?.invalidate() },
    retry = { sourceLiveData.value?.retryLoading() }
)