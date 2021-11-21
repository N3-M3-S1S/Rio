package com.nemesis.rio.presentation.profile.overview.character.raiding

import com.nemesis.rio.presentation.profile.overview.character.CharacterOverviewParentFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.factory
import org.koin.dsl.module

val characterRaidProgressModule = module {
    scope<CharacterOverviewParentFragment> {
        viewModel<CharacterRaidProgressViewModel>()
    }
    factory<CharacterRaidProgressListDataFactory>()
}
