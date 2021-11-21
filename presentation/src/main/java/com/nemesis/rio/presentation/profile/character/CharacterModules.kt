package com.nemesis.rio.presentation.profile.character

import com.nemesis.rio.data.api.refresh.CharacterLastCrawlDateTimeProvider
import com.nemesis.rio.data.api.refresh.ProfileLastCrawlDateTimeProvider
import com.nemesis.rio.data.database.AppDatabase
import com.nemesis.rio.data.profile.character.update.CharacterUpdateStrategy
import com.nemesis.rio.data.profile.database.ProfileDao
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.profile.update.usecase.ProfileUpdateStrategy
import com.nemesis.rio.presentation.profile.profileCoreDependencies
import org.koin.core.qualifier.qualifier
import org.koin.dsl.bind
import org.koin.dsl.factory
import org.koin.dsl.module

val characterQualifier = qualifier<Character>()

val characterModule = module {
    profileCoreDependencies<Character>()
    single(characterQualifier) { get<AppDatabase>().characterDao } bind ProfileDao::class
    factory<CharacterUpdateStrategy>(characterQualifier) bind ProfileUpdateStrategy::class
    factory<CharacterLastCrawlDateTimeProvider>(characterQualifier) bind ProfileLastCrawlDateTimeProvider::class
}
