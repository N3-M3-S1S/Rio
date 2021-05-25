package com.nemesis.rio.presentation.main

import androidx.lifecycle.ViewModel
import com.nemesis.rio.presentation.app.clipboard.Clipboard
import com.nemesis.rio.presentation.app.messages.MessageManager

class MainActivityViewModel(
    messageManager: MessageManager,
    private val clipboard: Clipboard,
) : ViewModel() {

    val messages = messageManager.messageEvent

    fun copyToClipboard(text: String) = clipboard.copyToClipboard(text)
}
