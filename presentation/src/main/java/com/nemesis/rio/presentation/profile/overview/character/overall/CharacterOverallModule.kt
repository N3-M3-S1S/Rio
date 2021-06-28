package com.nemesis.rio.presentation.profile.overview.character.overall

import com.nemesis.rio.presentation.main.navigateToProfileOverviewEventFlowQualifier
import com.nemesis.rio.presentation.profile.character.characterQualifier
import com.nemesis.rio.presentation.profile.overview.character.CharacterOverviewParentFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val characterOverallModule = module {
    scope<CharacterOverviewParentFragment> {
        viewModel {
            CharacterOverallViewModel(
                characterOverallDataFactory = get(),
                characterFlow = get(),
                searchGuild = get(),
                navigateToProfileOverviewEventFlow = get(navigateToProfileOverviewEventFlowQualifier),
                messageManager = get(),
                loadingStateController = get(),
                guildSearchActiveStateController = get()
            )
        }
    }
    factory {
        CharacterOverallDataFactory(
            getCurrentSeason = get(),
            getOverallMythicPlusScore = get(),
            getHexColorForMythicPlusScore = get(),
            getCurrentRaid = get(),
            getBestKillsForRaid = get(),
            getAchievementsForRaid = get(),
            getProfileLastRefreshDateTime = get(characterQualifier)
        )
    }
}
