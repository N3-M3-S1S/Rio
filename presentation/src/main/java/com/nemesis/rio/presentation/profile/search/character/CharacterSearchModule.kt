package com.nemesis.rio.presentation.profile.search.character

import com.nemesis.rio.data.api.search.database.CharacterSearchResponseSaver
import com.nemesis.rio.data.mplus.ranks.database.MythicPlusRanksSaver
import com.nemesis.rio.data.mplus.runs.database.MythicPlusRunsSaver
import com.nemesis.rio.data.mplus.scores.database.MythicPlusScoresSaver
import com.nemesis.rio.data.profile.character.api.CharacterSearchFields
import com.nemesis.rio.data.profile.character.search.CharacterSearchApiSource
import com.nemesis.rio.data.profile.character.search.CharacterSearchDatabaseSource
import com.nemesis.rio.data.profile.character.search.CharacterSearchSourceImpl
import com.nemesis.rio.data.profile.database.ProfileSaver
import com.nemesis.rio.data.raiding.achievements.database.RaidAchievementsSaver
import com.nemesis.rio.data.raiding.progress.database.RaidProgressSaver
import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.domain.profile.character.search.CharacterSearchSource
import com.nemesis.rio.domain.profile.character.search.usecase.SearchCharacter
import com.nemesis.rio.presentation.profile.character.characterQualifier
import org.koin.dsl.module
import org.koin.experimental.builder.factory
import org.koin.experimental.builder.factoryBy

val characterSearchModule = module {
    factoryBy<CharacterSearchSource, CharacterSearchSourceImpl>()
    factory<CharacterSearchApiSource>()
    factory<CharacterSearchFields.Factory>()
    factory { CharacterSearchDatabaseSource(characterDao = get(characterQualifier)) }

    factory {
        SearchCharacter(
            characterSearchSource = get(),
            characterSearchHistory = get(characterQualifier)
        )
    }

    factory {
        val characterSaver = get<ProfileSaver<Character>>(characterQualifier)
        val mythicPlusScoresSaver = get<MythicPlusScoresSaver>()
        val mythicPlusRunsSaver = get<MythicPlusRunsSaver>()
        val mythicPlusRanksScoresSaver = get<MythicPlusRanksSaver>()
        val raidProgressSaver = get<RaidProgressSaver>(characterQualifier)
        val raidAchievementsSaver = get<RaidAchievementsSaver>()
        CharacterSearchResponseSaver(
            characterSaver,
            mythicPlusScoresSaver,
            mythicPlusRanksScoresSaver,
            mythicPlusRunsSaver,
            raidAchievementsSaver,
            raidProgressSaver
        )
    }
}
