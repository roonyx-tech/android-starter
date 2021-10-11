package tech.roonyx.android_starter.common

sealed class NetworkState {
    object Loading : NetworkState()
    object Success : NetworkState()
    class Error(val exception: Throwable) : NetworkState()
}