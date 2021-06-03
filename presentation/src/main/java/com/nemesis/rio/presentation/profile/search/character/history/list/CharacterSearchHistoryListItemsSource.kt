package com.nemesis.rio.presentation.profile.search.character.history.list

import androidx.core.text.buildSpannedString
import androidx.core.text.color
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.profile.search.usecase.GetProfileSearchHistory
import com.nemesis.rio.presentation.profile.buildServerInfoString
import com.nemesis.rio.presentation.profile.character.attributes.colorResId
import com.nemesis.rio.presentation.profile.character.attributes.stringResId
import com.nemesis.rio.presentation.profile.search.history.AbstractProfileSearchHistoryListItemsSource
import com.nemesis.rio.presentation.profile.search.history.ProfileSearchHistoryItemActionsHandler
import splitties.resources.appColor
import splitties.resources.appStr

class CharacterSearchHistoryListItemsSource(
    getProfileSearchHistory: GetProfileSearchHistory<Character>,
    characterSearchHistoryActionsHandler: ProfileSearchHistoryItemActionsHandler<Character>
) : AbstractProfileSearchHistoryListItemsSource<Character>(
    getProfileSearchHistory,
    characterSearchHistoryActionsHandler
) {

    override fun getProfileDescription(profile: Character): CharSequence = buildSpannedString {
        val classColor = appColor(profile.attributes.characterClass.colorResId)
        color(classColor) {
            val className = appStr(profile.attributes.characterClass.stringResId)
            append(className)
        }
    }

    override fun getServerAndFactionText(profile: Character): CharSequence =
        with(profile) { buildServerInfoString(region, realm, faction) }
}
