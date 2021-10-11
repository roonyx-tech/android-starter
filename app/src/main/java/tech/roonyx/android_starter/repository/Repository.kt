package tech.roonyx.android_starter.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import tech.roonyx.android_starter.common.RetryListing
import tech.roonyx.android_starter.common.createRetryListing
import tech.roonyx.android_starter.data.remote.Api
import tech.roonyx.android_starter.repository.dataSource.PositionalNetworkStateFactory

class Repository(
    private val api: Api
) {

    suspend fun getMessage(): String {
        delay(2000) //Emulate API request
        return "Message"
    }

    /**
     * Example of using Listing
     */
    fun getMessageListing(
        viewModelScope: CoroutineScope,
        pageSize: Int
    ): RetryListing<String> = PositionalNetworkStateFactory(viewModelScope) { start, limit ->
        delay(100) //Emulate API request
        listOf("Message from page $start with limit $limit")
    }.createRetryListing(pageSize)
}