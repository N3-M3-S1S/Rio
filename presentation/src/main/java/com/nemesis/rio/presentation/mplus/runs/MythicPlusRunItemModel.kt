package com.nemesis.rio.presentation.mplus.runs

import android.content.Context
import android.graphics.Outline
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.core.view.setMargins
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import com.nemesis.rio.domain.mplus.runs.MythicPlusRun
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.databinding.ItemMplusRunContentBinding
import splitties.resources.dimen
import splitties.resources.dimenPxOffset
import splitties.resources.dimenPxSize

@EpoxyModelClass
abstract class MythicPlusRunItemModel : EpoxyModelWithView<ViewGroup>() {

    companion object {
        const val VIEW_TYPE = R.layout.item_mplus_run_content
    }

    @EpoxyAttribute
    lateinit var run: MythicPlusRun

    @EpoxyAttribute
    lateinit var onOpenRunInBrowserClicked: ((MythicPlusRun) -> Unit)

    private var setDatabindingValuesAfterInflation = false

    override fun bind(runViewParent: ViewGroup) {
        val mythicPlusRunBinding = getMythicPlusRunBindingIfReady(runViewParent)
        if (mythicPlusRunBinding != null) {
            setDatabindingValues(mythicPlusRunBinding)
        } else {
            setDatabindingValuesAfterInflation = true
        }
    }

    private fun getMythicPlusRunBindingIfReady(runParentView: ViewGroup): ItemMplusRunContentBinding? {
        val runContentView = runParentView.getChildAt(0)
        val isRunContentViewInflated = runContentView != null
        return if (isRunContentViewInflated) {
            runContentView.tag as? ItemMplusRunContentBinding
        } else {
            null
        }
    }

    private fun setDatabindingValues(binding: ItemMplusRunContentBinding) {
        binding.run = run
        binding.onOpenRunInBrowserClicked = onOpenRunInBrowserClicked
        binding.executePendingBindings()
    }

    override fun buildView(parent: ViewGroup): ViewGroup {
        val runParentView = createRunParentView(parent)
        createRunContentViewAsync(runParentView)
        return runParentView
    }

    private fun createRunParentView(thisViewParent: ViewGroup): ViewGroup =
        FrameLayout(thisViewParent.context).apply {
            layoutParams = createRunParentViewLayoutParams(context)
            setBackgroundResource(R.drawable.item_mplus_run_backgrund)
        }

    private fun createRunParentViewLayoutParams(context: Context) = RecyclerView.LayoutParams(
        RecyclerView.LayoutParams.MATCH_PARENT,
        context.dimenPxOffset(R.dimen.item_mplus_run_view_height)
    ).apply {
        setMargins(context.dimenPxOffset(R.dimen.space_normal))
    }

    private fun createRunContentViewAsync(runParentView: ViewGroup) {
        AsyncLayoutInflater(runParentView.context).inflate(
            R.layout.item_mplus_run_content,
            runParentView
        ) { runContentView, _, _ ->
            TransitionManager.beginDelayedTransition(runParentView, Fade())
            val dataBinding = setupDatabinding(runContentView)
            if (setDatabindingValuesAfterInflation) {
                setDatabindingValuesAfterInflation = false
                setDatabindingValues(dataBinding)
            }
            clipRunContentViewToParentViewBorder(runContentView, runParentView)
            runParentView.addView(runContentView)
        }
    }

    private fun setupDatabinding(runView: View): ItemMplusRunContentBinding {
        val dataBinding = ItemMplusRunContentBinding.bind(runView)
        runView.tag = dataBinding
        return dataBinding
    }

    private fun clipRunContentViewToParentViewBorder(
        runContentView: View,
        runParentView: ViewGroup
    ) {
        runContentView.outlineProvider = createRunParentViewBorderOutlineProvider(runParentView)
        runContentView.clipToOutline = true
    }

    private fun createRunParentViewBorderOutlineProvider(parent: ViewGroup) =
        object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                val borderWidth = parent.dimenPxSize(R.dimen.item_mplus_run_border_stroke_size)
                val borderCornerRadius = parent.dimen(R.dimen.item_mplus_run_border_corners_size)
                outline!!.setRoundRect(
                    borderWidth,
                    borderWidth,
                    parent.measuredWidth - borderWidth,
                    parent.measuredHeight - borderWidth,
                    borderCornerRadius
                )
            }
        }

    override fun unbind(view: ViewGroup) {
        super.unbind(view)
        setDatabindingValuesAfterInflation = false
        getMythicPlusRunBindingIfReady(view)?.apply {
            onOpenRunInBrowserClicked = null
            unbind()
        }
    }

    override fun getViewType(): Int = VIEW_TYPE
}
