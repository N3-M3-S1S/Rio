package com.nemesis.rio.presentation.profile.overview.character.mplus.ranks

import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.mplus.ranks.usecase.CharacterHasMythicPlusRanksForCurrentSeason
import com.nemesis.rio.domain.profile.Faction
import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.mplus.ranks.MythicPlusRanksType
import com.nemesis.rio.presentation.profile.stringResId
import com.nemesis.rio.presentation.ranks.list.RanksHeaderItem
import com.nemesis.rio.presentation.ranks.list.RanksListData
import com.nemesis.rio.presentation.ranks.list.RanksListItem
import splitties.resources.appStr

private typealias MutableRanksListData = MutableMap<RanksHeaderItem, List<RanksListItem>>

private fun mutableRanksListData() = mutableMapOf<RanksHeaderItem, List<RanksListItem>>()

class CharacterMythicPlusRanksDataFactory(
    private val characterHasMythicPlusRanksForCurrentSeason: CharacterHasMythicPlusRanksForCurrentSeason,
    private val ranksItemsFactories: Map<MythicPlusRanksType, MythicPlusRanksItemsFactory>
) {

    suspend fun getRanksDataForCurrentSeason(
        character: Character,
        ranksType: MythicPlusRanksType,
        ranksScope: MythicPlusRanksScope? = null,
    ): CharacterMythicPlusRanksData? {
        if (!characterHasMythicPlusRanksForCurrentSeason(character)) return null
        val ranksListData = createRanksListData(character, ranksType, ranksScope)
        return CharacterMythicPlusRanksData(
            character.faction,
            ranksType,
            ranksScope,
            ranksListData
        )
    }

    suspend fun updateRanksType(
        character: Character,
        ranksType: MythicPlusRanksType,
        oldData: CharacterMythicPlusRanksData
    ): CharacterMythicPlusRanksData {
        val ranksListData =
            createRanksListData(character, ranksType, oldData.selectedRanksScope)
        return oldData.copy(ranksListData = ranksListData, selectedRanksType = ranksType)
    }

    suspend fun updateRanksScope(
        character: Character,
        ranksScope: MythicPlusRanksScope?,
        oldData: CharacterMythicPlusRanksData
    ): CharacterMythicPlusRanksData {
        val ranksListData =
            createRanksListData(character, oldData.selectedRanksType, ranksScope)
        return oldData.copy(ranksListData = ranksListData, selectedRanksScope = ranksScope)
    }

    private suspend fun createRanksListData(
        character: Character,
        ranksType: MythicPlusRanksType,
        ranksScope: MythicPlusRanksScope?
    ): RanksListData {
        val ranksListData = mutableRanksListData()

        if (ranksScope == null) {
            createAndAddEntryToRanksListDataForAllScopes(character, ranksType, ranksListData)
        } else {
            createAndAddEntryToRanksListData(character, ranksType, ranksScope, ranksListData)
        }

        return ranksListData
    }

    private suspend fun createAndAddEntryToRanksListDataForAllScopes(
        character: Character,
        ranksType: MythicPlusRanksType,
        ranksListData: MutableRanksListData
    ) = enumValues<MythicPlusRanksScope>().map { ranksScope ->
        createAndAddEntryToRanksListData(character, ranksType, ranksScope, ranksListData)
    }

    private suspend fun createAndAddEntryToRanksListData(
        character: Character,
        ranksType: MythicPlusRanksType,
        ranksScope: MythicPlusRanksScope,
        ranksListData: MutableRanksListData
    ) {
        val ranksHeader = createRanksListHeader(character.faction, ranksScope)
        val ranksItems =
            ranksItemsFactories.getValue(ranksType).getRanksItems(character, ranksScope)
        ranksListData[ranksHeader] = ranksItems
    }

    private fun createRanksListHeader(
        faction: Faction,
        scope: MythicPlusRanksScope
    ): RanksHeaderItem {
        val titleId = when (scope) {
            MythicPlusRanksScope.GLOBAL -> R.string.ranks_span_global
            MythicPlusRanksScope.FACTION -> faction.stringResId
        }
        return appStr(titleId)
    }
}
