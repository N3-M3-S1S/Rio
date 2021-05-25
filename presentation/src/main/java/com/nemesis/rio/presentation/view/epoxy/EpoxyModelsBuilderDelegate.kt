package com.nemesis.rio.presentation.view.epoxy

import com.airbnb.epoxy.EpoxyController

interface EpoxyModelsBuilderDelegate<T : Any?> {
    fun buildModels(data: T, controller: EpoxyController)
}
