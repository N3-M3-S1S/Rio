package com.nemesis.rio.presentation.profile.character.attributes

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nemesis.rio.domain.profile.character.attributes.Spec
import com.nemesis.rio.presentation.R

val Spec.stringResId: Int
    @StringRes
    get() = when (this) {
        Spec.BREWMASTER -> R.string.spec_brewmaster
        Spec.WINDWALKER -> R.string.spec_windwalker
        Spec.MISTWEAVER -> R.string.spec_mistweaver
        Spec.ELEMENTAL -> R.string.spec_elemental
        Spec.ENHANCEMENT -> R.string.spec_enhancement
        Spec.RESTORATION_SHAMAN -> R.string.spec_restoration
        Spec.BEAST_MASTERY -> R.string.spec_beast_mastery
        Spec.MARKSMANSHIP -> R.string.spec_marksmanship
        Spec.SURVIVAL -> R.string.spec_survival
        Spec.PROTECTION_PALADIN -> R.string.spec_protection
        Spec.RETRIBUTION -> R.string.spec_retribution
        Spec.HOLY_PALADIN -> R.string.spec_holy
        Spec.AFFLICTION -> R.string.spec_affliction
        Spec.DESTRUCTION -> R.string.spec_destruction
        Spec.DEMONOLOGY -> R.string.spec_demonology
        Spec.ASSASSINATION -> R.string.spec_assassination
        Spec.SUBTLETY -> R.string.spec_subtlety
        Spec.OUTLAW -> R.string.spec_outlaw
        Spec.HAVOC -> R.string.spec_havoc
        Spec.VENGEANCE -> R.string.spec_vengeance
        Spec.PROTECTION_WARRIOR -> R.string.spec_protection
        Spec.ARMS -> R.string.spec_arms
        Spec.FURY -> R.string.spec_fury
        Spec.FROST_MAGE -> R.string.spec_frost
        Spec.FIRE -> R.string.spec_fire
        Spec.ARCANE -> R.string.spec_arcane
        Spec.FROST_DEATH_KNIGHT -> R.string.spec_frost
        Spec.BLOOD -> R.string.spec_blood
        Spec.UNHOLY -> R.string.spec_unholy
        Spec.HOLY_PRIEST -> R.string.spec_holy
        Spec.DISCIPLINE -> R.string.spec_discipline
        Spec.SHADOW -> R.string.spec_shadow
        Spec.RESTORATION_DRUID -> R.string.spec_restoration
        Spec.GUARDIAN -> R.string.spec_guardian
        Spec.FERAL -> R.string.spec_feral
        Spec.BALANCE -> R.string.spec_balance
    }

val Spec.iconResId: Int
    @DrawableRes
    get() = when (this) {
        Spec.AFFLICTION -> R.drawable.spec_warlock_affliction
        Spec.DESTRUCTION -> R.drawable.spec_warlock_destruction
        Spec.DEMONOLOGY -> R.drawable.spec_warlock_demonology
        Spec.ASSASSINATION -> R.drawable.spec_rogue_assassination
        Spec.SUBTLETY -> R.drawable.spec_rogue_subtlety
        Spec.OUTLAW -> R.drawable.spec_rogue_outlaw
        Spec.HAVOC -> R.drawable.spec_demon_hunter_havoc
        Spec.VENGEANCE -> R.drawable.spec_demon_hunter_vengence
        Spec.PROTECTION_WARRIOR -> R.drawable.spec_warrior_protection
        Spec.ARMS -> R.drawable.spec_warrior_arms
        Spec.FURY -> R.drawable.spec_warrior_fury
        Spec.FROST_MAGE -> R.drawable.spec_mage_frost
        Spec.FIRE -> R.drawable.spec_mage_fire
        Spec.ARCANE -> R.drawable.spec_mage_arcane
        Spec.FROST_DEATH_KNIGHT -> R.drawable.spec_death_knight_frost
        Spec.BLOOD -> R.drawable.spec_death_knight_blood
        Spec.UNHOLY -> R.drawable.spec_death_knight_unholy
        Spec.HOLY_PRIEST -> R.drawable.spec_priest_holy
        Spec.DISCIPLINE -> R.drawable.spec_priest_discipline
        Spec.SHADOW -> R.drawable.spec_priest_shadow
        Spec.RESTORATION_DRUID -> R.drawable.spell_druid_restoration
        Spec.GUARDIAN -> R.drawable.spec_druid_guardian
        Spec.FERAL -> R.drawable.spec_druid_feral
        Spec.BALANCE -> R.drawable.spec_druid_balance
        Spec.BREWMASTER -> R.drawable.spec_monk_brewmaster
        Spec.WINDWALKER -> R.drawable.spec_monk_windwalker
        Spec.MISTWEAVER -> R.drawable.spec_monk_mistweaver
        Spec.RESTORATION_SHAMAN -> R.drawable.spec_shaman_restoration
        Spec.ELEMENTAL -> R.drawable.spec_shaman_elemental
        Spec.ENHANCEMENT -> R.drawable.spec_shaman_enhancement
        Spec.BEAST_MASTERY -> R.drawable.spec_hunter_beast_mastery
        Spec.MARKSMANSHIP -> R.drawable.spec_hunter_marksmanship
        Spec.SURVIVAL -> R.drawable.spec_hunter_survival
        Spec.PROTECTION_PALADIN -> R.drawable.spec_paladin_protection
        Spec.RETRIBUTION -> R.drawable.spec_paladin_retribution
        Spec.HOLY_PALADIN -> R.drawable.spec_paladin_holy
    }
