package com.nemesis.rio.presentation.profile.overview.guild.ranks

import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.profile.Guild
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.ranks.RaidRanks
import com.nemesis.rio.domain.raiding.ranks.usecase.GetAllRaidRanks
import com.nemesis.rio.presentation.raiding.stringResId
import com.nemesis.rio.presentation.ranks.list.RanksListData
import com.nemesis.rio.presentation.ranks.list.RanksListItem
import splitties.resources.appStr

class GuildRaidRanksListDataFactory(val getAllRaidRanks: GetAllRaidRanks) {

    suspend fun getGuildRaidRanksListData(guild: Guild): GuildRaidRanksListData {
        val raidRandRanks = getAllRaidRanks(guild)
        return if (raidRandRanks.isEmpty()) emptyMap()
        else {
            mapRaidAndRanksToGuildRaidRanksListData(raidRandRanks)
        }
    }

    private fun mapRaidAndRanksToGuildRaidRanksListData(raidAndRanks: Map<Raid, RaidRanks>): GuildRaidRanksListData {
        val raidAndRanksGroupedByExpansion = groupRaidAndRanksByExpansion(raidAndRanks)
        return raidAndRanksGroupedByExpansion.mapValues { (_, raidAndRanks) ->
            mapRaidAndRanksToRanksListData(raidAndRanks)
        }
    }

    private fun groupRaidAndRanksByExpansion(raidAndRanks: Map<Raid, RaidRanks>): Map<Expansion, Map<Raid, RaidRanks>> {
        val result = mutableMapOf<Expansion, MutableMap<Raid, RaidRanks>>()
        raidAndRanks.forEach { (raid, ranks) ->
            val expansion = raid.expansion
            if (!result.containsKey(expansion)) {
                result[expansion] = mutableMapOf()
            }

            result.getValue(expansion)[raid] = ranks
        }
        return result
    }

    private fun mapRaidAndRanksToRanksListData(raidAndRanks: Map<Raid, RaidRanks>): RanksListData =
        raidAndRanks.entries.associate { (raid, ranks) ->
            val listHeader = appStr(raid.stringResId)
            val listItems = createRaidRanksListItems(ranks)
            listHeader to listItems
        }

    private fun createRaidRanksListItems(raidRanks: RaidRanks): List<RanksListItem> = raidRanks
        .toSortedMap(compareByDescending { it })
        .map { (difficulty, ranks) -> RanksListItem(appStr(difficulty.stringResId), ranks) }
}
