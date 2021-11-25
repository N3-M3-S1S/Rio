package com.nemesis.rio.presentation.profile.overview.character.mplus.ranks

import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.mplus.ranks.usecase.GetClassMythicPlusRanksForCurrentSeason
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.presentation.profile.character.attributes.iconResId
import com.nemesis.rio.presentation.profile.character.attributes.stringResId
import com.nemesis.rio.presentation.ranks.list.RanksListItem
import splitties.resources.appStr

class MythicPlusClassRanksItemsFactory(
    private val getClassMythicPlusRanksForCurrentSeason: GetClassMythicPlusRanksForCurrentSeason
) :
    MythicPlusRanksItemsFactory {

    override suspend fun getRanksItems(
        character: Character,
        scope: MythicPlusRanksScope
    ): List<RanksListItem> {
        val classRanks = getClassMythicPlusRanksForCurrentSeason(character, scope)
        val characterClass = character.attributes.characterClass
        val classRanksItems = mutableListOf<RanksListItem>()

        // if a character has ranks only for one role, no need to add ranks item for class because class ranks and ranks for that role will be the same. (e.g. rogue can be damage dealer only, so ranks for rogues and ranks for rogues damage dealers will be the same)
        val characterHasRanksForMultipleRoles = classRanks.ranksForClassRoles.size > 1
        if (characterHasRanksForMultipleRoles) {
            val ranksItemForClass = RanksListItem(
                appStr(characterClass.stringResId),
                classRanks.ranksForClass,
                characterClass.iconResId
            )
            classRanksItems.add(ranksItemForClass)
        }

        classRanks.ranksForClassRoles.mapTo(classRanksItems) { (role, ranks) ->
            val className = appStr(characterClass.stringResId)
            val roleName = appStr(role.stringResId)
            val title = "$className $roleName"
            RanksListItem(
                title,
                ranks,
                role.iconResId
            )
        }

        return classRanksItems
    }
}
