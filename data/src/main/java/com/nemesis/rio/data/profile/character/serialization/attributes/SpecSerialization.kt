package com.nemesis.rio.data.profile.character.serialization.attributes

import com.nemesis.rio.domain.profile.character.attributes.CharacterClass
import com.nemesis.rio.domain.profile.character.attributes.Spec
import com.nemesis.rio.utils.searchEnumByName

internal object SpecSerialization {
    private val Spec.id: Int
        get() = when (this) {
            Spec.AFFLICTION -> 265
            Spec.DESTRUCTION -> 267
            Spec.DEMONOLOGY -> 266

            Spec.ASSASSINATION -> 259
            Spec.SUBTLETY -> 261
            Spec.OUTLAW -> 260

            Spec.HAVOC -> 577
            Spec.VENGEANCE -> 581

            Spec.PROTECTION_WARRIOR -> 73
            Spec.ARMS -> 71
            Spec.FURY -> 72

            Spec.FROST_MAGE -> 64
            Spec.FIRE -> 63
            Spec.ARCANE -> 62

            Spec.FROST_DEATH_KNIGHT -> 251
            Spec.BLOOD -> 250
            Spec.UNHOLY -> 252

            Spec.HOLY_PRIEST -> 257
            Spec.DISCIPLINE -> 256
            Spec.SHADOW -> 258

            Spec.RESTORATION_DRUID -> 105
            Spec.GUARDIAN -> 104
            Spec.FERAL -> 103
            Spec.BALANCE -> 102

            Spec.BREWMASTER -> 268
            Spec.WINDWALKER -> 269
            Spec.MISTWEAVER -> 270

            Spec.RESTORATION_SHAMAN -> 264
            Spec.ELEMENTAL -> 262
            Spec.ENHANCEMENT -> 263

            Spec.BEAST_MASTERY -> 253
            Spec.MARKSMANSHIP -> 254
            Spec.SURVIVAL -> 255

            Spec.PROTECTION_PALADIN -> 66
            Spec.RETRIBUTION -> 70
            Spec.HOLY_PALADIN -> 65
        }

    fun parseSpecById(specId: Int): Spec =
        enumValues<Spec>().first { spec -> spec.id == specId }

    // TODO replace with actual values from the api
    fun parseSpecByJsonValue(
        characterClass: CharacterClass,
        specJsonValue: String,
    ): Spec = when (specJsonValue) {

        "Restoration" -> {
            if (characterClass == CharacterClass.DRUID) {
                Spec.RESTORATION_DRUID
            } else {
                Spec.RESTORATION_SHAMAN
            }
        }

        "Holy" -> {
            if (characterClass == CharacterClass.PALADIN) {
                Spec.HOLY_PALADIN
            } else {
                Spec.HOLY_PRIEST
            }
        }

        "Frost" -> {
            if (characterClass == CharacterClass.DEATH_KNIGHT) {
                Spec.FROST_DEATH_KNIGHT
            } else {
                Spec.FROST_MAGE
            }
        }

        "Protection" -> {
            if (characterClass == CharacterClass.PALADIN) {
                Spec.PROTECTION_PALADIN
            } else {
                Spec.PROTECTION_WARRIOR
            }
        }

        else -> searchEnumByName<Spec>(specJsonValue.replace(" ", "_"))
            ?: error("Unknown spec json value: $specJsonValue")
    }
}
