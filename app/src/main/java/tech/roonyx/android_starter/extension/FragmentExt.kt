package tech.roonyx.android_starter.extension

import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ArgumentsProperty<T : Fragment, R>(
    private val key: String
) : ReadOnlyProperty<T, R> {

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: T, property: KProperty<*>): R {
        return thisRef.arguments?.get(key) as? R
            ?: throw IllegalArgumentException("Use newInstance() fun!")
    }
}

fun <T : Fragment, R> argument(key: String) = ArgumentsProperty<T, R>(key)

fun Fragment.hideKeyboard() {
    context?.let { context ->
        view?.let { v ->
            hideKeyboard(context, v)
        }
    }
}