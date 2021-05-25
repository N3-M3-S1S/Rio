package com.nemesis.rio.presentation.profile.overview.raiding

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.os.bundleOf
import androidx.core.view.setMargins
import androidx.transition.AutoTransition
import androidx.transition.ChangeTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.OnViewRecycled
import com.airbnb.epoxy.preload.Preloadable
import com.nemesis.rio.domain.raiding.Difficulty
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.achievements.AheadOfTheCurve
import com.nemesis.rio.domain.raiding.achievements.CuttingEdge
import com.nemesis.rio.domain.raiding.achievements.RaidAchievement
import com.nemesis.rio.domain.raiding.progress.RaidProgress
import com.nemesis.rio.domain.raiding.progress.bestProgress
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.app.defaultBindingAdapters
import com.nemesis.rio.presentation.raiding.imageResId
import com.nemesis.rio.presentation.raiding.setRaidName
import com.nemesis.rio.presentation.raiding.setRaidProgress
import com.nemesis.rio.presentation.raiding.stringResId
import splitties.resources.dimenPxSize
import splitties.resources.str
import splitties.views.inflate
import splitties.views.onClick

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, saveViewState = true)
class RaidProgressItemModelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : CardView(context, attrs, defStyleAttr), Preloadable {
    private val superStateKey = "super"
    private val expandedStateKey = "expanded"
    private var isExpanded = false
    private val constraintsConfiguration = ConstraintsConfiguration()
    lateinit var raid: Raid
    private lateinit var raidProgress: RaidProgress
    private lateinit var raidAchievements: List<RaidAchievement>
    override val viewsToPreload: List<View>
        get() = listOf(findViewById(R.id.raid_progress_raid_image))

    init {
        inflate<View>(R.layout.item_raid_progress_collapsed, true)
        onClick { toggleExpandedState() }
        setRaidProgressDifficultyTitles()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        (layoutParams as MarginLayoutParams).setMargins(dimenPxSize(R.dimen.space_normal))
        constraintsConfiguration.applyConstraints(animate = false)
    }

    @ModelProp
    fun raidProgressListItem(item: RaidProgressItem) {
        raid = item.raid
        raidProgress = item.raidProgress
        raidAchievements = item.raidAchievements
        setRaidNameAndImage()
        setRaidProgressValuesAndConstraints()
        setRaidAchievementsValuesAndConstraints()
        constraintsConfiguration.applyConstraints(animate = false)
    }

    private fun toggleExpandedState() {
        isExpanded = !isExpanded
        constraintsConfiguration.applyConstraints(animate = true)
    }

    private fun setRaidNameAndImage() {
        findViewById<TextView>(R.id.raid_progress_raid_name).setRaidName(raid)
        findViewById<ImageView>(R.id.raid_progress_raid_image).load(raid.imageResId) {
            crossfade(true)
        }
    }

    private fun setRaidProgressValuesAndConstraints() {
        val hasProgress = raidProgress.isNotEmpty()
        constraintsConfiguration.setProgressViewsVisibility(hasProgress)
        if (hasProgress) {
            setRaidProgressDifficultyTitles()
            setRaidProgressValues()
        }
    }

    private fun setRaidProgressDifficultyTitles() {
        enumValues<Difficulty>().forEach { difficulty ->
            val difficultyTitleTextView: TextView = when (difficulty) {
                Difficulty.NORMAL -> R.id.raid_progress_normal_title
                Difficulty.HEROIC -> R.id.raid_progress_heroic_title
                Difficulty.MYTHIC -> R.id.raid_progress_mythic_title
            }.let(::findViewById)
            val difficultyTitle =
                str(R.string.raid_progress_difficulty_title_format, str(difficulty.stringResId))
            difficultyTitleTextView.text = difficultyTitle
        }
    }

    private fun setRaidProgressValues() {
        setBestRaidProgressValues()
        setRaidProgressDifficultyKills()
    }

    private fun setBestRaidProgressValues() {
        val (difficulty, bossesKilled) = raidProgress.bestProgress()
        findViewById<TextView>(R.id.raid_progress_best_progress).setRaidProgress(
            bossesKilled, raid.bossesCount, difficulty
        )
    }

    private fun setRaidProgressDifficultyKills() {
        enumValues<Difficulty>().forEach { difficulty ->
            val difficultyKillsTextView: TextView = when (difficulty) {
                Difficulty.NORMAL -> R.id.raid_progress_normal_kills
                Difficulty.HEROIC -> R.id.raid_progress_heroic_kills
                Difficulty.MYTHIC -> R.id.raid_progress_mythic_kills
            }.let(::findViewById)
            difficultyKillsTextView
                .setRaidProgress(raidProgress.getOrDefault(difficulty, 0), raid.bossesCount)
        }
    }

    private fun setRaidAchievementsValuesAndConstraints() {
        raidAchievements.forEach { achievement ->
            constraintsConfiguration.setAchievementViewsVisibility(achievement, true)
            setAchievementDateTime(achievement)
        }
    }

    private fun setAchievementDateTime(achievement: RaidAchievement) {
        findViewById<TextView>(achievement.dateTimeViewID).apply {
            defaultBindingAdapters.setLocalDateTime(this, achievement.date)
        }
    }

    private val RaidAchievement.dateTimeViewID
        get() = when (this) {
            is AheadOfTheCurve -> R.id.raid_progress_achievement_aotc_date
            is CuttingEdge -> R.id.raid_progress_achievement_ce_date
        }

    /*if, for example, the view was bound to a model that had AheadOfTheCurve and CuttingEdge raidAchievements, but then was reused for a model with empty raidAchievements, the view will have visible raidAchievements views from previous model
     *this method will be called when the view is recycled and unbound from its model, so it's a good place to hide raidAchievements views from previous model
     */
    @OnViewRecycled
    fun onRecycled() {
        raidAchievements.forEach {
            constraintsConfiguration.setAchievementViewsVisibility(it, false)
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        return bundleOf(
            expandedStateKey to isExpanded,
            superStateKey to super.onSaveInstanceState()
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        require(state is Bundle)
        val superState = state.getParcelable<Parcelable>(superStateKey)
        super.onRestoreInstanceState(superState)
        isExpanded = state.getBoolean(expandedStateKey)
    }

    private inner class ConstraintsConfiguration {
        private val collapsedStateConstraintSet =
            loadConstraintSetFromLayout(R.layout.item_raid_progress_collapsed)
        private val expandedStateConstraintSet =
            loadConstraintSetFromLayout(R.layout.item_raid_progress_expanded)
        private val animationTransitionSet by lazy {
            TransitionSet().apply {
                addTransition(ChangeTransform())
                addTransition(AutoTransition())
                duration = 200L
            }
        }

        private fun loadConstraintSetFromLayout(@LayoutRes layoutID: Int) =
            ConstraintSet().apply { load(context, layoutID) }

        fun applyConstraints(animate: Boolean) {
            if (animate) {
                prepareTransitionManager()
            }

            val constraintSet = getConstraintSetForExpansionState()

            constraintSet.applyTo(findViewById(R.id.raid_progress_root))
        }

        private fun prepareTransitionManager() {
            TransitionManager.beginDelayedTransition(
                parent as ViewGroup,
                animationTransitionSet
            )
        }

        private fun getConstraintSetForExpansionState() =
            if (isExpanded) expandedStateConstraintSet else collapsedStateConstraintSet

        fun setProgressViewsVisibility(isVisible: Boolean) {
            collapsedStateConstraintSet.setVisibility(R.id.raid_progress_best_progress, isVisible)
            expandedStateConstraintSet.setVisibility(R.id.raid_progress_flow, isVisible)
        }

        fun setAchievementViewsVisibility(achievement: RaidAchievement, isVisible: Boolean) {
            val achievementNameViewID = achievement.titleViewID
            val achievementDateViewID = achievement.dateTimeViewID
            arrayOf(collapsedStateConstraintSet, expandedStateConstraintSet).forEach {
                it.setVisibility(achievementNameViewID, isVisible)
            }
            expandedStateConstraintSet.setVisibility(achievementDateViewID, isVisible)
        }

        private fun ConstraintSet.setVisibility(@IdRes viewID: Int, visible: Boolean) {
            val visibility = if (visible) View.VISIBLE else View.GONE
            setVisibility(viewID, visibility)
        }

        private val RaidAchievement.titleViewID
            get() = when (this) {
                is AheadOfTheCurve -> R.id.raid_progress_achievement_aotc_title
                is CuttingEdge -> R.id.raid_progress_achievement_ce_title
            }
    }
}
