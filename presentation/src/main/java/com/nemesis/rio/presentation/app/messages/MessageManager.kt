package com.nemesis.rio.presentation.app.messages

import com.nemesis.rio.presentation.app.applicationScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MessageManager {
    private val _messageEvent = MutableSharedFlow<Message>()
    val messageEvent: Flow<Message> = _messageEvent

    fun sendMessage(message: Message) {
        applicationScope.launch {
            _messageEvent.emit(message)
        }
    }
}

fun MessageManager.logAndSendExceptionMessage(exception: Throwable) {
    Timber.e(exception)
    sendMessage(exceptionMessage(exception))
}
