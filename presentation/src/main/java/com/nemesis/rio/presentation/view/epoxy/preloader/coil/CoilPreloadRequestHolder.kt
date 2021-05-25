package com.nemesis.rio.presentation.view.epoxy.preloader.coil

import android.content.Context
import coil.Coil
import coil.ImageLoader
import coil.request.Disposable
import coil.request.ImageRequest
import com.airbnb.epoxy.preload.PreloadRequestHolder

class CoilPreloadRequestHolder(
    private val context: Context,
    private val imageLoader: ImageLoader = Coil.imageLoader(context)
) : PreloadRequestHolder {
    private var disposable: Disposable? = null

    fun enqueueRequest(setupRequest: ImageRequest.Builder.() -> Unit) {
        disposable = ImageRequest.Builder(context)
            .apply {
                setupRequest()
                target(null) // preload request should not have a target
            }
            .build()
            .let(imageLoader::enqueue)
    }

    override fun clear() {
        disposable?.dispose()
    }
}
