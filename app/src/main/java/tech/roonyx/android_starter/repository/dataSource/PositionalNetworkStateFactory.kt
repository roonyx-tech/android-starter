package tech.roonyx.android_starter.repository.dataSource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope

class PositionalNetworkStateFactory<T>(
    private val coroutineScope: CoroutineScope,
    private val fetchDataCallback: suspend (startAt: Int, limit: Int) -> List<T>
) : DataSource.Factory<Int, T>() {

    private val source = MutableLiveData<PositionalNetworkStateDataSource<T>>()
    val sourceLiveData: LiveData<PositionalNetworkStateDataSource<T>> get() = source

    override fun create(): DataSource<Int, T> {
        return object : PositionalNetworkStateDataSource<T>(coroutineScope) {
            override suspend fun fetchData(startAt: Int, limit: Int): List<T> {
                return fetchDataCallback(startAt, limit)
            }
        }.apply { source.postValue(this) }
    }
}