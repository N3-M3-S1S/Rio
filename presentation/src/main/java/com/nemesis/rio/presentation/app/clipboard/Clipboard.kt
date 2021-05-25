package com.nemesis.rio.presentation.app.clipboard

import android.content.ClipData
import splitties.systemservices.clipboardManager

class Clipboard {
    fun copyToClipboard(text: String) =
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", text))
}
