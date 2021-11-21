package com.nemesis.rio.presentation.ranks

import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import coil.imageLoader
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.nemesis.rio.presentation.R
import splitties.resources.dimenPxSize
import splitties.views.setCompoundDrawables

@BindingAdapter("rankSpan")
fun TextView.setRankSpan(rankSpan: RanksSpan?) {
    rankSpan?.run { setText(stringResId) }
}

@BindingAdapter("ranksIcon")
fun TextView.setRanksIcon(@DrawableRes iconResId: Int?) {
    iconResId?.let {
        ImageRequest.Builder(context)
            .data(iconResId)
            .size(dimenPxSize(R.dimen.item_ranks_icon_size))
            .transformations(CircleCropTransformation())
            .target { icon -> setCompoundDrawables(start = icon, intrinsicBounds = true); }
            .build()
            .let { imageRequest -> context.imageLoader.enqueue(imageRequest) }
    }
}
