package com.nemesis.rio.presentation.view.epoxy.preloader.coil

import android.content.Context
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.epoxy.preload.EpoxyModelPreloader
import com.airbnb.epoxy.preload.PreloadErrorHandler
import com.airbnb.epoxy.preload.ViewData
import com.airbnb.epoxy.preload.ViewMetadata

fun <T : EpoxyModel<*>, U : ViewMetadata?> EpoxyRecyclerView.addCoilPreloader(
    context: Context,
    maxPreloadDistance: Int = 3,
    errorHandler: PreloadErrorHandler = { _, err -> throw(err) },
    preloader: EpoxyModelPreloader<T, U, CoilPreloadRequestHolder>
) {
    addPreloader(maxPreloadDistance, errorHandler, preloader) { CoilPreloadRequestHolder(context) }
}

fun CoilPreloadRequestHolder.loadData(data: Any?, viewData: ViewData<ViewMetadata?>) {
    val width = viewData.width
    val height = viewData.height
    enqueueRequest {
        size(width, height)
        data(data)
    }
}
