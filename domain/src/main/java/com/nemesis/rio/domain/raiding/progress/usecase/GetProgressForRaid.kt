package com.nemesis.rio.domain.raiding.progress.usecase

import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.domain.profile.guild.Guild
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.progress.RaidProgress
import com.nemesis.rio.domain.raiding.source.CharacterRaidingSource
import com.nemesis.rio.domain.raiding.source.GuildRaidingSource

class GetProgressForRaid(
    private val characterRaidingSource: CharacterRaidingSource,
    private val guildRaidingSource: GuildRaidingSource
) {

    suspend operator fun invoke(character: Character, raid: Raid): RaidProgress =
        characterRaidingSource.getProgressForRaid(character, raid)

    suspend operator fun invoke(guild: Guild, raid: Raid): RaidProgress =
        guildRaidingSource.getProgressForRaid(guild, raid)
}
