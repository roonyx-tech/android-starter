package tech.roonyx.android_starter.repository.dataSource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import kotlinx.coroutines.*
import tech.roonyx.android_starter.common.NetworkState

abstract class PositionalNetworkStateDataSource<T>(
    private val coroutineScope: CoroutineScope
) : PositionalDataSource<T>() {

    private val initialState = MutableLiveData<NetworkState>()
    private val loadingState = MutableLiveData<NetworkState>()

    val initialStateLiveData: LiveData<NetworkState> get() = initialState
    val loadingStateLiveData: LiveData<NetworkState> get() = loadingState

    private var retryAction: (() -> Unit)? = null
    private var job: Job? = null

    abstract suspend fun fetchData(startAt: Int, limit: Int): List<T>

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        job = coroutineScope.launch {
            initialState.postValue(NetworkState.Loading)
            withContext(Dispatchers.IO) {
                runCatching {
                    fetchData(params.requestedStartPosition, params.requestedLoadSize)
                }.fold(
                    onSuccess = {
                        initialState.postValue(NetworkState.Success)
                        retryAction = null
                        callback.onResult(it, params.requestedStartPosition)
                    },
                    onFailure = {
                        initialState.postValue(NetworkState.Error(it))
                        retryAction = { loadInitial(params, callback) }
                        Log.d(TAG, "onFailure", it)
                    }
                )
            }
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        if (params.startPosition < params.loadSize) {
            callback.onResult(emptyList())
            return
        }

        job = coroutineScope.launch {
            loadingState.postValue(NetworkState.Loading)
            withContext(Dispatchers.IO) {
                runCatching {
                    fetchData(params.startPosition, params.loadSize)
                }.fold(
                    onSuccess = {
                        loadingState.postValue(NetworkState.Success)
                        retryAction = null
                        callback.onResult(it)
                    },
                    onFailure = {
                        loadingState.postValue(NetworkState.Error(it))
                        retryAction = { loadRange(params, callback) }
                        Log.d(TAG, "onFailure", it)
                    }
                )
            }
        }
    }

    fun retryLoading() {
        if (job?.isActive == false) retryAction?.invoke()
    }

    override fun invalidate() {
        job?.cancel()
        super.invalidate()
    }

    companion object {
        private const val TAG = "NetworkStateDataSource"
    }
}