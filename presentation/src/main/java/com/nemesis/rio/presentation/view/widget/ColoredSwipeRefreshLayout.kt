package com.nemesis.rio.presentation.view.widget

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.withStyledAttributes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.utils.extensions.getIntOrNull

class ColoredSwipeRefreshLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
) : SwipeRefreshLayout(context, attributeSet) {

    init {
        context.withStyledAttributes(
            set = attributeSet,
            attrs = R.styleable.ColoredSwipeRefreshLayout
        ) {
            val progressBarColor =
                getIntOrNull(R.styleable.ColoredSwipeRefreshLayout_csrl_progressbarColor)
            val progressBarBackgroundColor =
                getIntOrNull(R.styleable.ColoredSwipeRefreshLayout_csrl_progressbarBackgroundColor)
            progressBarColor?.let { setColorSchemeColors(progressBarColor) }
            progressBarBackgroundColor?.let(::setProgressBackgroundColorSchemeColor)
        }
    }
}
