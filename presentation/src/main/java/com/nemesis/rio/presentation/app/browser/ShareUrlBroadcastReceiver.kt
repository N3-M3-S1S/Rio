package com.nemesis.rio.presentation.app.browser

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ShareUrlBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val url = intent!!.dataString ?: return
        val shareUrlIntent = createShareUrlIntent(url)
        context?.startActivity(shareUrlIntent)
    }

    private fun createShareUrlIntent(url: String): Intent {
        val sendUrlIntent = createSendUrlIntent(url)
        return Intent.createChooser(sendUrlIntent, null)
            .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
    }

    private fun createSendUrlIntent(url: String): Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }
}
