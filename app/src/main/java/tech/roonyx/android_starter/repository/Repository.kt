package tech.roonyx.android_starter.repository

import kotlinx.coroutines.delay
import tech.roonyx.android_starter.data.remote.Api

class Repository(
    private val api: Api
) {

    suspend fun getMessage(): String {
        delay(2000)
        return "Message"
    }
}