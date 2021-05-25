package com.nemesis.rio.domain.profile.character.attributes

data class CharacterAttributes(
    val characterClass: CharacterClass,
    val activeSpec: Spec,
    val race: Race,
    val covenant: Covenant?
)
