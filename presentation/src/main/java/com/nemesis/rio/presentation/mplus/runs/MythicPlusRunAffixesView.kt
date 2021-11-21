package com.nemesis.rio.presentation.mplus.runs

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toRectF
import coil.imageLoader
import coil.memory.MemoryCache
import com.nemesis.rio.domain.mplus.Affix
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.mplus.imageResId
import splitties.resources.dimen
import splitties.resources.dimenPxSize
import splitties.resources.styledColor

class MythicPlusRunAffixesView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint()
    private val affixIconSize = dimenPxSize(R.dimen.item_mplus_run_affix_icon_size)
    private val affixIconBorderWidth = dimenPxSize(R.dimen.item_mplus_run_affix_icon_border_size)
    private val affixIconBorderColor = styledColor(R.attr.colorControlNormal)
    private val affixIconBorderCornersSize = dimen(R.dimen.item_mplus_run_affix_icon_border_corners_size)
    private val spaceBetweenIcons = dimenPxSize(R.dimen.item_mplus_run_affix_icon_space)
    private val affixesIconsBitmaps = ArrayList<Bitmap>(4)
    private lateinit var coilMemoryCache: MemoryCache

    init {
        if (isInEditMode) { // for android studio preview
            val testAffixes = enumValues<Affix>().take(4).toList()
            setAffixes(testAffixes)
        } else {
            coilMemoryCache = context.imageLoader.memoryCache!!
        }
    }

    fun setAffixes(affixes: Collection<Affix>) {
        val affixesCountChanged = affixesIconsBitmaps.size != affixes.size
        initializeAffixesIconBitmaps(affixes)
        if (affixesCountChanged) {
            requestLayout()
        }
        paint.reset()
        invalidate()
    }

    // TODO coil 2.0 memory cache bitmap
    private fun initializeAffixesIconBitmaps(affixes: Collection<Affix>) {
        affixesIconsBitmaps.clear()
        if (isInEditMode) { // for android studio preview
            affixes.mapTo(affixesIconsBitmaps, ::createAffixIconBitmap)
        } else {
            affixes.mapTo(affixesIconsBitmaps) { affix ->
                val affixIconBitmapMemoryCacheKey = MemoryCache.Key(affix.name)
                var affixIconBitmap = coilMemoryCache[affixIconBitmapMemoryCacheKey]

                if (affixIconBitmap == null) {
                    affixIconBitmap = createAffixIconBitmap(affix).let(MemoryCache::Value)
                    coilMemoryCache[affixIconBitmapMemoryCacheKey] = affixIconBitmap
                }

                affixIconBitmap.bitmap
            }
        }
    }

    private fun createAffixIconBitmap(affix: Affix): Bitmap {
        val resultBitmap = createResultBitmap()
        val canvas = Canvas(resultBitmap)
        val affixIconBitmap = decodeAffixIconBitmapFromResources(affix)
        drawAffixIconWithRoundedCorners(canvas, affixIconBitmap)
        drawBorderWithRoundedCorners(canvas)
        return resultBitmap
    }

    private fun createResultBitmap() =
        Bitmap.createBitmap(affixIconSize, affixIconSize, Bitmap.Config.ARGB_8888)

    private fun decodeAffixIconBitmapFromResources(affix: Affix) =
        BitmapFactory.decodeResource(resources, affix.imageResId)

    private fun drawAffixIconWithRoundedCorners(canvas: Canvas, affixIconBitmap: Bitmap) {
        paint.reset()
        paint.isAntiAlias = true
        val affixIconRectangleSize = affixIconSize - affixIconBorderWidth

        val affixIconRectangle =
            Rect(
                affixIconBorderWidth,
                affixIconBorderWidth,
                affixIconRectangleSize,
                affixIconRectangleSize
            )

        // draw a rectangle with rounded corners
        canvas.drawRoundRect(
            affixIconRectangle.toRectF(),
            affixIconBorderCornersSize,
            affixIconBorderCornersSize,
            paint
        )

        // do not draw pixels that DON'T COVER pixels of the rectangle with rounded corners, this makes the bitmap have rounded corners
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        val scaledAffixIconBitmap = Bitmap.createScaledBitmap(
            affixIconBitmap,
            affixIconRectangle.width(),
            affixIconRectangle.height(),
            false
        )
        canvas.drawBitmap(scaledAffixIconBitmap, null, affixIconRectangle, paint)
    }

    private fun drawBorderWithRoundedCorners(canvas: Canvas) {
        paint.reset()
        paint.isAntiAlias = true
        val borderRectangle = RectF(0f, 0f, affixIconSize.toFloat(), affixIconSize.toFloat())
        paint.color = affixIconBorderColor

        // do not draw pixels of borderRectangle that COVER affix icon
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
        canvas.drawRoundRect(
            borderRectangle,
            affixIconBorderCornersSize,
            affixIconBorderCornersSize,
            paint
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width =
            affixesIconsBitmaps.size * affixIconSize + (spaceBetweenIcons * (affixesIconsBitmaps.size - 1))
        setMeasuredDimension(width, affixIconSize)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        affixesIconsBitmaps.forEachIndexed { index, bitmap ->
            val left = index * affixIconSize + (index * spaceBetweenIcons)
            canvas!!.drawBitmap(bitmap, left.toFloat(), 0f, paint)
        }
    }
}
