package com.nemesis.rio.presentation.profile.overview.character.overall

import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.databinding.BindingAdapter
import coil.load
import com.nemesis.rio.domain.mplus.scores.color.HexColor
import com.nemesis.rio.domain.profile.character.attributes.CharacterAttributes
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.profile.character.attributes.colorResId
import com.nemesis.rio.presentation.profile.character.attributes.stringResId
import com.nemesis.rio.presentation.utils.increaseForegroundHexColorBrightnessToWCAGAAStandard
import splitties.resources.color
import splitties.resources.str

@BindingAdapter("characterOverall_attributes", "characterOverall_itemLevel")
fun TextView.setCharacterOverallAttributesAndItemLevel(
    attributes: CharacterAttributes?,
    itemLevel: Int
) = attributes?.let {
    text = buildSpannedString {
        val raceName = str(it.race.stringResId)
        append("$raceName ")

        val classColor = color(it.characterClass.colorResId)
        color(classColor) {
            val specName = str(it.activeSpec.stringResId)
            val className = str(it.characterClass.stringResId)
            append("$specName $className ")
        }

        val itemLevelString = str(R.string.character_overall_item_level_format, itemLevel)
        append(itemLevelString)
    }
}

@BindingAdapter("characterOverall_profileImageUrl")
fun ImageView.setCharacterOverallProfileImage(profileImageUrl: String?) {
    profileImageUrl?.let {
        load(it) {
            placeholder(R.drawable.fragment_character_overall_profile_image_placeholder)
            crossfade(true)
        }
    }
}

@BindingAdapter("characterOverall_scoreColor")
fun TextView.setCharacterOverallScoreColor(scoreColor: HexColor?) {
    scoreColor
        ?.let(::increaseForegroundHexColorBrightnessToWCAGAAStandard)
        ?.let(::setTextColor)
}
