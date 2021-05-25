package com.nemesis.rio.presentation.view.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.core.content.withStyledAttributes
import androidx.core.os.bundleOf
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.material.button.MaterialButton
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.utils.extensions.getIntOrNull
import splitties.resources.styledColor

class MaterialProgressButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialButtonStyle,
) : MaterialButton(context, attributeSet, defStyleAttr) {
    private val progressVisibleKey = "progress"
    private val superStateKey = "super"
    private val buttonTextColorKey = "color"
    private var isProgressVisible: Boolean = false
    private var buttonTextColor = currentTextColor
    private var progressDrawable = ProgressDrawable(styledColor(R.attr.colorPrimary))

    init {
        // get values from xml
        context.withStyledAttributes(
            set = attributeSet,
            attrs = R.styleable.MaterialProgressButton
        ) {
            isProgressVisible =
                getBoolean(R.styleable.MaterialProgressButton_mpb_progressVisibility, false)

            getIntOrNull(R.styleable.MaterialProgressButton_mpb_progressbarColor)
                ?.let { progressDrawable.color = it }
        }
        setProgressVisibility()
    }

    // data binding uses this method (e.g. one can set LiveData<Boolean> to isProgressVisible parameter in xml and when livedata's value changes this method will be called)
    fun setProgressVisible(visible: Boolean) {
        if (visible != isProgressVisible) {
            isProgressVisible = visible
            setProgressVisibility()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // I'm not sure about this method. I guess it will give progressDrawable a rectangle whose width and height equals to width and height of the button
        // and when progressDrawable's onDraw method is called it will draw itself in the middle of that rectangle
        progressDrawable.setBounds(measuredWidth, measuredHeight, 0, 0)
    }

    override fun onSaveInstanceState() = bundleOf(
        superStateKey to super.onSaveInstanceState(),
        progressVisibleKey to isProgressVisible,
        buttonTextColorKey to buttonTextColor
    )

    override fun onRestoreInstanceState(state: Parcelable?) {
        with(state as Bundle) {
            val superState = getParcelable<Parcelable>(superStateKey)
            super.onRestoreInstanceState(superState)
            isProgressVisible = getBoolean(progressVisibleKey, false)
            buttonTextColor = getInt(buttonTextColorKey)
            setProgressVisibility()
        }
    }

    private fun setProgressVisibility() {
        if (isProgressVisible) {
            showProgressBar()
        } else {
            showButtonText()
        }
    }

    private fun showProgressBar() {
        buttonTextColor = currentTextColor // backup current text color
        progressDrawable.start() // start progress bar spinning animation
        setTextColor(Color.TRANSPARENT) // make text transparent, this will trigger onDraw
    }

    private fun showButtonText() {
        progressDrawable.stop() // stop progress bar spinning animation
        setTextColor(buttonTextColor) // restore text color from backup,this will trigger onDraw
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (isProgressVisible) {
            progressDrawable.draw(canvas!!)
        }
    }

    private inner class ProgressDrawable(@ColorInt color: Int) : CircularProgressDrawable(context),
        Drawable.Callback {

        @ColorInt
        var color: Int = 0
            set(value) {
                setColorSchemeColors(value)
                field = value
            }

        init {
            setStyle(DEFAULT)
            callback = this
            this.color = color
        }

        override fun invalidateDrawable(who: Drawable) {
            this@MaterialProgressButton.invalidate() // redraw the button when animation changes
        }

        override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {
            // unused
        }

        override fun unscheduleDrawable(who: Drawable, what: Runnable) {
            // unused
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (progressDrawable.isRunning) {
            progressDrawable.stop()
        }
    }
}
