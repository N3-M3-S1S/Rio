package com.nemesis.rio.presentation.mplus.runs

import com.airbnb.epoxy.preload.EpoxyModelPreloader
import com.airbnb.epoxy.preload.ViewData
import com.airbnb.epoxy.preload.ViewMetadata
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.mplus.imageResId
import com.nemesis.rio.presentation.view.epoxy.preloader.coil.CoilPreloadRequestHolder
import com.nemesis.rio.presentation.view.epoxy.preloader.coil.loadData

val mythicPlusRunItemModelPreloader =
    EpoxyModelPreloader.with(
        listOf(R.id.run_root)
    ) { epoxyModel: MythicPlusRunItemModel, preloadTarget: CoilPreloadRequestHolder, viewData: ViewData<ViewMetadata?> ->
        val dungeonImageResId = epoxyModel.run.dungeon.imageResId
        preloadTarget.loadData(dungeonImageResId, viewData)
    }
