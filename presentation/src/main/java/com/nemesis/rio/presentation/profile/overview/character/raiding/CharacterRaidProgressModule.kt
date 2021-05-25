package com.nemesis.rio.presentation.profile.overview.character.raiding

import com.nemesis.rio.presentation.profile.overview.character.CharacterOverviewParentFragment
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val characterRaidProgressModule = module {
    scope<CharacterOverviewParentFragment> {
        viewModel<CharacterRaidProgressViewModel>()
    }
    factory<CharacterRaidProgressListDataFactory>()
}
