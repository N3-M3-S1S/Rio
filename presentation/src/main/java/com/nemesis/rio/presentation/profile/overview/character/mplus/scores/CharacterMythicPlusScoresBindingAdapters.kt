package com.nemesis.rio.presentation.profile.overview.character.mplus.scores

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.nemesis.rio.domain.mplus.scores.color.HexColor
import com.nemesis.rio.presentation.utils.increaseForegroundHexColorBrightnessToWCAGAAStandard

@BindingAdapter("characterMplusScore_scoreColor")
fun TextView.setCharacterMythicPlusScoreColor(scoreColor: HexColor?) {
    scoreColor
        ?.let(::increaseForegroundHexColorBrightnessToWCAGAAStandard)
        ?.let(::setTextColor)
}
