package com.nemesis.rio.presentation.profile.character.attributes

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import com.nemesis.rio.domain.profile.character.attributes.CharacterClass
import com.nemesis.rio.presentation.R

val CharacterClass.stringResId
    @StringRes
    get() = when (this) {
        CharacterClass.DEATH_KNIGHT -> R.string.class_death_knight
        CharacterClass.DEMON_HUNTER -> R.string.class_demon_hunter
        CharacterClass.DRUID -> R.string.class_druid
        CharacterClass.HUNTER -> R.string.class_hunter
        CharacterClass.MAGE -> R.string.class_mage
        CharacterClass.MONK -> R.string.class_monk
        CharacterClass.PALADIN -> R.string.class_paladin
        CharacterClass.PRIEST -> R.string.class_priest
        CharacterClass.ROGUE -> R.string.class_rogue
        CharacterClass.SHAMAN -> R.string.class_shaman
        CharacterClass.WARLOCK -> R.string.class_warlock
        CharacterClass.WARRIOR -> R.string.class_warrior
    }

val CharacterClass.themeResId
    @StyleRes
    get() = when (this) {
        CharacterClass.DEATH_KNIGHT -> R.style.AppTheme_DeathKnight
        CharacterClass.DEMON_HUNTER -> R.style.AppTheme_DemonHunter
        CharacterClass.DRUID -> R.style.AppTheme_Druid
        CharacterClass.HUNTER -> R.style.AppTheme_Hunter
        CharacterClass.MAGE -> R.style.AppTheme_Mage
        CharacterClass.MONK -> R.style.AppTheme_Monk
        CharacterClass.PALADIN -> R.style.AppTheme_Paladin
        CharacterClass.PRIEST -> R.style.AppTheme_Priest
        CharacterClass.ROGUE -> R.style.AppTheme_Rogue
        CharacterClass.SHAMAN -> R.style.AppTheme_Shaman
        CharacterClass.WARLOCK -> R.style.AppTheme_Warlock
        CharacterClass.WARRIOR -> R.style.AppTheme_Warrior
    }

val CharacterClass.colorResId
    @ColorRes
    get() = when (this) {
        CharacterClass.DEATH_KNIGHT -> R.color.class_death_knight
        CharacterClass.DEMON_HUNTER -> R.color.class_demon_hunter
        CharacterClass.DRUID -> R.color.class_druid
        CharacterClass.HUNTER -> R.color.class_hunter
        CharacterClass.MAGE -> R.color.class_mage
        CharacterClass.MONK -> R.color.class_monk
        CharacterClass.PALADIN -> R.color.class_paladin
        CharacterClass.PRIEST -> R.color.class_priest
        CharacterClass.ROGUE -> R.color.class_rogue
        CharacterClass.SHAMAN -> R.color.class_shaman
        CharacterClass.WARLOCK -> R.color.class_warlock
        CharacterClass.WARRIOR -> R.color.class_warrior
    }

val CharacterClass.iconResId: Int
    @DrawableRes
    get() = when (this) {
        CharacterClass.DEATH_KNIGHT -> R.drawable.class_death_knight
        CharacterClass.DEMON_HUNTER -> R.drawable.class_demon_hunter
        CharacterClass.DRUID -> R.drawable.class_druid
        CharacterClass.HUNTER -> R.drawable.class_hunter
        CharacterClass.MAGE -> R.drawable.class_mage
        CharacterClass.MONK -> R.drawable.class_monk
        CharacterClass.PALADIN -> R.drawable.class_paladin
        CharacterClass.PRIEST -> R.drawable.class_priest
        CharacterClass.ROGUE -> R.drawable.class_rogue
        CharacterClass.SHAMAN -> R.drawable.class_shaman
        CharacterClass.WARLOCK -> R.drawable.class_warlock
        CharacterClass.WARRIOR -> R.drawable.class_warrior
    }
