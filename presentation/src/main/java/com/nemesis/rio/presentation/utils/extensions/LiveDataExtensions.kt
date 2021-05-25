package com.nemesis.rio.presentation.utils.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

val <T> LiveData<T>.notNullValue
    get() = requireNotNull(value)

fun <T> MutableLiveData<T>.updateValue(func: (T) -> T) {
    val newValue = func(notNullValue)
    value = newValue
}

/**
 * Unlike [androidx.lifecycle.asLiveData], collection of the flow wont be canceled if the livedata becomes inactive.
 */
fun <T> Flow<T>.toLiveData(coroutineScope: CoroutineScope): LiveData<T> {
    val liveData = MutableLiveData<T>()
    coroutineScope.launch {
        collect(liveData::setValue)
    }
    return liveData
}
