package com.nemesis.rio.presentation.app.browser

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import com.nemesis.rio.presentation.R
import splitties.init.appCtx
import splitties.resources.appColor
import splitties.resources.appStr

class UrlBrowser {
    private val customTabIntent = CustomTabsIntent.Builder()
        .also(::setupShareUrlActionButton)
        .also(::setToolbarSurfaceColor)
        .build()

    fun openUrl(context: Context, url: String) {
        customTabIntent.launchUrl(context, url.toUri())
    }

    private fun setupShareUrlActionButton(customTabsIntentBuilder: CustomTabsIntent.Builder) {
        val icon = getShareIcon()
        val description = getShareDescription()
        val pendingIntent = createShareUrlPendingIntent()
        customTabsIntentBuilder.setActionButton(icon, description, pendingIntent, true)
    }

    private fun getShareIcon() =
        BitmapFactory.decodeResource(appCtx.resources, R.drawable.ic_share)

    private fun getShareDescription() = appStr(R.string.share)

    private fun createShareUrlPendingIntent(): PendingIntent {
        val intent = Intent(appCtx, ShareUrlBroadcastReceiver::class.java)
        return PendingIntent.getBroadcast(appCtx, 0, intent, 0)
    }

    private fun setToolbarSurfaceColor(customTabsIntentBuilder: CustomTabsIntent.Builder) {
        val color = appColor(R.color.surface)
        val customTabColorSchemeParams = CustomTabColorSchemeParams.Builder().setToolbarColor(color).build()
        customTabsIntentBuilder.setDefaultColorSchemeParams(customTabColorSchemeParams)
    }
}
