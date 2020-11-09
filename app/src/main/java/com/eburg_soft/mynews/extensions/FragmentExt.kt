package com.eburg_soft.mynews.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eburg_soft.mynews.data.di.ToothpickViewModelFactory
import com.eburg_soft.mynews.data.di.ToothpickViewModelFactoryScoped
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

fun <T : Any?> Fragment.observe(liveData: LiveData<T>?, observer: (T) -> Unit) {
    liveData?.observe(viewLifecycleOwner, {
        it?.let(observer)
    })
}

fun <T : ViewModel> Fragment.injectViewModel(modelClass: KClass<T>, customScope: Any? = null): T {
    val factory = if (customScope == null) ToothpickViewModelFactory else ToothpickViewModelFactoryScoped(customScope)
    return ViewModelProvider(this, factory).get(modelClass.java)
}

fun ViewGroup.inflate(@LayoutRes layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this.context).inflate(layoutId, this, attachToRoot)
}

fun <T> Fragment.viewLifecycleLazy(initialise: () -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {

        // A backing property to hold our value
        private var binding: T? = null

        private var viewLifecycleOwner: LifecycleOwner? = null

        init {
            // Observe the View Lifecycle of the Fragment
            this@viewLifecycleLazy
                .viewLifecycleOwnerLiveData
                .observe(this@viewLifecycleLazy, { newLifecycleOwner ->
                    viewLifecycleOwner
                        ?.lifecycle
                        ?.removeObserver(this)

                    viewLifecycleOwner = newLifecycleOwner.also {
                        it.lifecycle.addObserver(this)
                    }
                })
        }

        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            binding = null
        }

        override fun getValue(
            thisRef: Fragment,
            property: KProperty<*>
        ): T {
            // Return the backing property if it's set, or initialise
            return this.binding ?: initialise().also {
                this.binding = it
            }
        }
    }

//fun <T> Fragment.viewLifecycleLazy(initialise: () -> T): ReadOnlyProperty<Fragment, T> =
//    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {
//
//        private var binding: T? = null
//
//        override fun onDestroy(owner: LifecycleOwner) {
//            binding = null
//        }
//
//        override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
//            binding
//                ?: initialise().also {
//                    binding = it
//                    this@viewLifecycleLazy.viewLifecycleOwner.lifecycle.addObserver(this)
//                }
//    }