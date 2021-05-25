package com.nemesis.rio.presentation.profile.character.attributes

import androidx.annotation.StringRes
import com.nemesis.rio.domain.profile.character.attributes.Race
import com.nemesis.rio.presentation.R

val Race.stringResId: Int
    @StringRes
    get() = when (this) {
        Race.HUMAN -> R.string.race_human
        Race.DWARF -> R.string.race_dwarf
        Race.DARK_IRON_DWARF -> R.string.race_dark_iron_dwarf
        Race.NIGHT_ELF -> R.string.race_night_elf
        Race.GNOME -> R.string.race_gnome
        Race.DRAENEI -> R.string.race_draenei
        Race.WORGEN -> R.string.race_worgen
        Race.VOID_ELF -> R.string.race_void_elf
        Race.LIGHTFORGED_DRAENEI -> R.string.race_lightforged_draenei
        Race.KUL_TIRAN -> R.string.race_kul_tiran
        Race.MECHANOGNOME -> R.string.race_mechanognome
        Race.ORC -> R.string.race_orc
        Race.UNDEAD -> R.string.race_undead
        Race.TAUREN -> R.string.race_tauren
        Race.TROLL -> R.string.race_troll
        Race.BLOOD_ELF -> R.string.race_blood_elf
        Race.GOBLIN -> R.string.race_goblin
        Race.NIGHTBORNE -> R.string.race_nightborne
        Race.HIGHMOUNTAIN_TAUREN -> R.string.race_highmountain_tauren
        Race.MAGHAR_ORC -> R.string.race_maghar_orc
        Race.ZANDALARI_TROLL -> R.string.race_zandalari_troll
        Race.VULPERA -> R.string.race_vulpera
        Race.PANDAREN -> R.string.race_pandaren
    }
