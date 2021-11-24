package com.nemesis.rio.presentation.profile.overview.character.mplus

import com.nemesis.rio.presentation.main.openUrlInBrowserEventFlowQualifier
import com.nemesis.rio.presentation.mplus.ranks.MythicPlusRanksType
import com.nemesis.rio.presentation.profile.overview.character.CharacterOverviewParentFragment
import com.nemesis.rio.presentation.profile.overview.character.mplus.ranks.*
import com.nemesis.rio.presentation.profile.overview.character.mplus.runs.CharacterMythicPlusRunsDataActionsHandler
import com.nemesis.rio.presentation.profile.overview.character.mplus.runs.CharacterMythicPlusRunsDataFactory
import com.nemesis.rio.presentation.profile.overview.character.mplus.runs.CharacterMythicPlusRunsDataModelsBuilder
import com.nemesis.rio.presentation.profile.overview.character.mplus.scores.CharacterMythicPlusScoresDataActionsHandler
import com.nemesis.rio.presentation.profile.overview.character.mplus.scores.CharacterMythicPlusScoresDataFactory
import com.nemesis.rio.presentation.profile.overview.character.mplus.scores.CharacterMythicPlusScoresDataModelsBuilder
import com.nemesis.rio.presentation.profile.overview.character.mplus.scores.CharacterMythicPlusScoresSeasonSelectController
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.qualifier
import org.koin.dsl.bind
import org.koin.dsl.factory
import org.koin.dsl.module

val characterMythicPlusModule = module {
    val ranksItemsFactoriesQualifier = qualifier("ranks-items-factories")

    scope<CharacterOverviewParentFragment> {
        viewModel {
            CharacterMythicPlusViewModel(
                characterFlow = get(),
                getExpansionsWithScores = get(),
                getSeasonsWithScoresForExpansion = get(),
                scoresDataFactory = get(),
                ranksDataFactory = get(),
                runsDataFactory = get(),
                openUrlInBrowserEventFlow = get(
                    openUrlInBrowserEventFlowQualifier
                ),
                loadingStateController = get()
            )
        }
    }

    factory<CharacterMythicPlusScoresDataFactory>()
    factory<CharacterMythicPlusRunsDataFactory>()
    factory<MythicPlusSpecRanksItemsFactory>(qualifier(MythicPlusRanksType.SPEC)) bind MythicPlusRanksItemsFactory::class
    factory<MythicPlusClassRanksItemsFactory>(qualifier(MythicPlusRanksType.CLASS)) bind MythicPlusRanksItemsFactory::class
    factory<MythicPlusOverallRanksItemsFactory>(qualifier(MythicPlusRanksType.OVERALL)) bind MythicPlusRanksItemsFactory::class

    factory { (handler: CharacterMythicPlusScoresDataActionsHandler) ->
        val scoresModelsBuilder =
            get<CharacterMythicPlusScoresDataModelsBuilder> { parametersOf(handler) }

        val ranksModelsBuilder =
            get<CharacterMythicPlusRanksDataModelsBuilder> { parametersOf(handler) }

        val runsModelsBuilder =
            get<CharacterMythicPlusRunsDataModelsBuilder> { parametersOf(handler) }

        CharacterMythicPlusDataController(
            scoresModelsBuilder,
            ranksModelsBuilder,
            runsModelsBuilder
        )
    }

    factory { (scoresDataActionsHandler: CharacterMythicPlusScoresDataActionsHandler) ->
        CharacterMythicPlusScoresDataModelsBuilder(scoresDataActionsHandler)
    }

    factory { (ranksDataActionsHandler: CharacterMythicPlusRanksDataActionsHandler) ->
        CharacterMythicPlusRanksDataModelsBuilder(ranksDataActionsHandler)
    }

    factory { (runsDataActionsHandler: CharacterMythicPlusRunsDataActionsHandler) ->
        CharacterMythicPlusRunsDataModelsBuilder(runsDataActionsHandler)
    }

    factory {
        CharacterMythicPlusRanksDataFactory(get(), get(ranksItemsFactoriesQualifier))
    }

    factory(ranksItemsFactoriesQualifier) {
        enumValues<MythicPlusRanksType>()
            .associateWith { get<MythicPlusRanksItemsFactory>(qualifier(it)) }
    }

    factory<CharacterMythicPlusScoresSeasonSelectController>()
}
