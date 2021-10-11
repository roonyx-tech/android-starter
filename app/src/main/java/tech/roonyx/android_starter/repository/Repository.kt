package tech.roonyx.android_starter.repository

import tech.roonyx.android_starter.data.remote.Api

class Repository(
    private val api: Api
) {

    fun getMessage(): String = "Message"
}