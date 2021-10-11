package tech.roonyx.android_starter.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content

    fun consume() {
        hasBeenHandled = true
    }
}

/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [Event]'s contents has not been handled.
 */
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            onEventUnhandledContent(value)
        }
    }
}

class EventObserverWithNull<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        if (event?.hasBeenHandled == false) {
            onEventUnhandledContent(event.peekContent())
            event.consume()
        }
    }
}

fun <A> A.toEvent() = Event(this)

fun MutableLiveData<Event<Unit>>.call() {
    value = Event(Unit)
}

fun <T> MutableLiveData<Event<T>>.call(data: T) {
    value = Event(data)
}

fun MutableLiveData<Event<Unit>>.post() {
    postValue(Event(Unit))
}

fun <T> MutableLiveData<Event<T>>.post(data: T) {
    postValue(Event(data))
}