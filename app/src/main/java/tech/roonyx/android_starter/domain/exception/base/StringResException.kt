package tech.roonyx.android_starter.domain.exception.base

import androidx.annotation.StringRes

abstract class StringResException(@StringRes val stringRes: Int) : Exception()