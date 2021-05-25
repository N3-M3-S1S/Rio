package com.nemesis.rio.presentation.view.epoxy

import android.content.Context
import android.util.AttributeSet
import com.airbnb.epoxy.EpoxyRecyclerView

class EpoxyRecyclerViewWithoutSharedPool @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : EpoxyRecyclerView(context, attrs, defStyleAttr) {

    override fun shouldShareViewPoolAcrossContext(): Boolean = false
}
