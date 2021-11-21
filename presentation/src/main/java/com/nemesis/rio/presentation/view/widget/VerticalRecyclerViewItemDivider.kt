package com.nemesis.rio.presentation.view.widget

import android.content.Context
import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import splitties.resources.styledDrawable

class VerticalRecyclerViewItemDivider(context: Context) : RecyclerView.ItemDecoration() {
    private val divider = context.styledDrawable(android.R.attr.listDivider)!!

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val dividerLeft = parent.paddingLeft
        val dividerRight = parent.width - parent.paddingRight
        val childCount = parent.layoutManager!!.itemCount
        for (i in 0 until childCount - 1) { // Do not draw the divider after the last item
            val child: View? = parent.getChildAt(i)
            if (child != null) {
                val params =
                    child.layoutParams as RecyclerView.LayoutParams
                val dividerTop: Int = child.bottom + params.bottomMargin
                val dividerBottom = dividerTop + (divider.intrinsicHeight)
                divider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
                divider.draw(c)
            }
        }
    }
}
