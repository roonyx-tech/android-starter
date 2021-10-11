package tech.roonyx.android_starter.extension

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ViewBindingProperty<T : ViewBinding>(
    private val bind: (view: View) -> T
) : ReadOnlyProperty<Fragment, T> {

    private var viewBinding: T? = null
    private val lifecycleObserver = BindingLifecycleObserver()

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        viewBinding?.let { return it }

        val view = thisRef.requireView()
        thisRef.viewLifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        return bind(view).also { viewBinding = it }
    }

    private inner class BindingLifecycleObserver : LifecycleObserver {

        private val mainHandler = Handler(Looper.getMainLooper())

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy(owner: LifecycleOwner) {
            // Fragment.viewLifecycleOwner call LifecycleObserver.onDestroy()
            // before Fragment.onDestroyView().
            // That's why we need to postpone reset of the viewBinding
            owner.lifecycle.removeObserver(this)
            mainHandler.post {
                viewBinding = null
                Log.d("ViewBindingProperty", "Clear ViewBinding")
            }
        }
    }
}

fun <T : ViewBinding> viewBinding(
    bind: (view: View) -> T
): ReadOnlyProperty<Fragment, T> = ViewBindingProperty(bind)