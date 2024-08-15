package com.example.mvvmarchitecturedatabinding.Model
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val observers = mutableSetOf<Observer<in T>>()

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            if (hasActiveObservers()) {
                // Handle multiple observers
            }
            observer.onChanged(it)
        })
        observers.add(observer)
    }

    fun call() {
        value = null
    }
}
