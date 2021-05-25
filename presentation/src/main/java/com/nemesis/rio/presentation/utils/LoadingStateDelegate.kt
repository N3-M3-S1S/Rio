package com.nemesis.rio.presentation.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

interface LoadingStateDelegate {
    val isLoading: LiveData<Boolean>
}

/**The idea is similar to [androidx.core.widget.ContentLoadingProgressBar]
 * If isLoading state is changing too fast - wait a minimum time before applying the change
 * */
class LoadingStateController(isLoading: Boolean = false) : LoadingStateDelegate {
    private var setLoadingStateJob: Job? = null
    private var loadingStartTimeStamp = -1L
    private val loadingStateMinimumDurationMillis = 500L
    private val _isLoading = MutableLiveData(isLoading)
    override val isLoading: LiveData<Boolean> = _isLoading

    /**
     * Cancel scheduled _isLoading change state and apply new state without delay
     */
    fun setLoading(isLoading: Boolean) {
        cancelSetLoadingJobIfActive()
        setLoadingState(isLoading)
    }

    fun setLoadingDelayed(isLoading: Boolean, coroutineScope: CoroutineScope) {
        cancelSetLoadingJobIfActive()
        val delay = getDelayBeforeApplyingLoadingState(isLoading)
        if (delay <= 0L) {
            setLoadingState(isLoading)
        } else {
            setLoadingStateJob = coroutineScope.launch {
                delay(delay)
                setLoadingState(isLoading)
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        loadingStartTimeStamp = if (isLoading) System.currentTimeMillis() else -1L
        _isLoading.value = isLoading
    }

    private fun getDelayBeforeApplyingLoadingState(isLoading: Boolean) =
        if (isLoading) {
            loadingStateMinimumDurationMillis
        } else {
            val millisSinceLoadingStarted = getMillisSinceLoadingStarted()
            if (wasLoadingInStartedStateLongEnough(millisSinceLoadingStarted)) {
                0
            } else {
                getRemainingLoadingDuration(millisSinceLoadingStarted)
            }
        }

    private fun getMillisSinceLoadingStarted() =
        System.currentTimeMillis() - loadingStartTimeStamp

    private fun cancelSetLoadingJobIfActive() {
        if (setLoadingStateJob != null && setLoadingStateJob!!.isActive) {
            setLoadingStateJob!!.cancel()
        }
    }

    private fun getRemainingLoadingDuration(millisSinceLoadingStarted: Long) =
        loadingStateMinimumDurationMillis - millisSinceLoadingStarted

    private fun wasLoadingInStartedStateLongEnough(millisSinceLoadingStarted: Long) =
        millisSinceLoadingStarted > loadingStateMinimumDurationMillis || loadingStartTimeStamp == -1L
}

inline fun <T, R> Flow<T>.mapWithDelayedLoading(
    loadingStateController: LoadingStateController,
    coroutineScope: CoroutineScope,
    crossinline transform: suspend (value: T) -> R,
) = map {
    loadingStateController.setLoadingDelayed(true, coroutineScope)
    val transformResult = transform(it)
    loadingStateController.setLoadingDelayed(false, coroutineScope)
    transformResult
}
