package com.nemesis.rio.presentation.mplus.scores

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.nemesis.rio.presentation.utils.toPrettyString

@BindingAdapter("mythicPlusScore")
fun TextView.setMythicPlusScore(score: Float) {
    text = score.toPrettyString()
}
