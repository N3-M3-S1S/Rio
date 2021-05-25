package com.nemesis.rio.domain.mplus.runs

import com.nemesis.rio.domain.profile.character.Character

interface MythicPlusRunsSource {
    suspend fun getRunsForCurrentSeason(character: Character): List<MythicPlusRun>
}
