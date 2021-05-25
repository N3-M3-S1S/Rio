package com.nemesis.rio.domain.raiding.progress.usecase

import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.domain.profile.guild.Guild
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.progress.RaidProgress
import com.nemesis.rio.domain.raiding.source.CharacterRaidingSource
import com.nemesis.rio.domain.raiding.source.GuildRaidingSource

class GetAllRaidProgress(
    private val guildRaidingSource: GuildRaidingSource,
    private val characterRaidingSource: CharacterRaidingSource
) {

    suspend operator fun invoke(guild: Guild): Map<Raid, RaidProgress> =
        guildRaidingSource.getAllRaidProgress(guild)

    suspend operator fun invoke(character: Character): Map<Raid, RaidProgress> =
        characterRaidingSource.getAllRaidProgress(character)
}
