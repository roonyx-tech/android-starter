package tech.roonyx.android_starter.extension

import android.content.Context
import retrofit2.HttpException
import tech.roonyx.android_starter.R
import tech.roonyx.android_starter.domain.exception.MessageException
import tech.roonyx.android_starter.domain.exception.base.StringResException
import java.net.UnknownHostException

fun Throwable.getMessageUI(context: Context): String = when (this) {
    is MessageException -> message
    is UnknownHostException -> context.getString(R.string.network_error)
    is HttpException -> context.getString(R.string.server_error)
    is StringResException -> context.getString(stringRes)
    else -> context.getString(R.string.loading_error)
}