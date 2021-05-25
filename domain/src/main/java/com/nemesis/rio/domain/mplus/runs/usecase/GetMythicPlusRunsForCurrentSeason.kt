package com.nemesis.rio.domain.mplus.runs.usecase

import com.nemesis.rio.domain.mplus.runs.MythicPlusRunsSource
import com.nemesis.rio.domain.profile.character.Character

class GetMythicPlusRunsForCurrentSeason(private val runsSource: MythicPlusRunsSource) {

    suspend operator fun invoke(character: Character) =
        runsSource.getRunsForCurrentSeason(character)
}
