package com.nemesis.rio.presentation.profile.overview.character

import android.util.SparseArray
import androidx.fragment.app.Fragment
import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.profile.overview.character.mplus.CharacterMythicPlusFragment
import com.nemesis.rio.presentation.profile.overview.character.mplus.characterMythicPlusModule
import com.nemesis.rio.presentation.profile.overview.character.overall.CharacterOverallFragment
import com.nemesis.rio.presentation.profile.overview.character.overall.characterOverallModule
import com.nemesis.rio.presentation.profile.overview.character.raiding.CharacterRaidProgressFragment
import com.nemesis.rio.presentation.profile.overview.character.raiding.characterRaidProgressModule
import com.nemesis.rio.presentation.profile.overview.profileOverviewCoreDependencies
import org.koin.dsl.module
import kotlin.reflect.KClass

val characterOverviewModule = module {
    scope<CharacterOverviewParentFragment> {
        profileOverviewCoreDependencies<Character>()
        factory { createCharacterOverviewChildFragmentNavigator() }
    }
}

private fun createCharacterOverviewChildFragmentNavigator(): com.nemesis.rio.presentation.profile.overview.ProfileOverviewChildFragmentsNavigator {
    val menuId = R.menu.fragment_character_overall_bottom_bar_menu
    val menuItemIdToFragmentClass = SparseArray<KClass<out Fragment>>(3).apply {
        append(R.id.character_overview_overall, CharacterOverallFragment::class)
        append(R.id.character_overview_mplus, CharacterMythicPlusFragment::class)
        append(R.id.character_overview_raid_progress, CharacterRaidProgressFragment::class)
    }
    return com.nemesis.rio.presentation.profile.overview.ProfileOverviewChildFragmentsNavigator(
        menuId,
        menuItemIdToFragmentClass
    )
}

val characterOverviewModules =
    listOf(
        characterOverviewModule,
        characterOverallModule,
        characterMythicPlusModule,
        characterRaidProgressModule
    )
