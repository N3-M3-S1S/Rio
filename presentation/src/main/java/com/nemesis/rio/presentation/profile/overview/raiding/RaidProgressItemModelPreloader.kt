package com.nemesis.rio.presentation.profile.overview.raiding

import com.airbnb.epoxy.preload.EpoxyModelPreloader
import com.airbnb.epoxy.preload.ViewData
import com.airbnb.epoxy.preload.ViewMetadata
import com.nemesis.rio.presentation.raiding.imageResId
import com.nemesis.rio.presentation.view.epoxy.preloader.coil.CoilPreloadRequestHolder
import com.nemesis.rio.presentation.view.epoxy.preloader.coil.loadData

val raidProgressItemModelPreloader =
    EpoxyModelPreloader.with { epoxyModel: RaidProgressItemModelView, preloadTarget: CoilPreloadRequestHolder, viewData: ViewData<ViewMetadata?> ->
        val raidImageResId = epoxyModel.raid.imageResId
        preloadTarget.loadData(raidImageResId, viewData)
    }
