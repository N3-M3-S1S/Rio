package com.nemesis.rio.presentation.profile.overview.character.mplus

import com.airbnb.epoxy.TypedEpoxyController
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.itemCenterText
import com.nemesis.rio.presentation.profile.overview.character.mplus.ranks.CharacterMythicPlusRanksData
import com.nemesis.rio.presentation.profile.overview.character.mplus.runs.CharacterMythicPlusRunsData
import com.nemesis.rio.presentation.profile.overview.character.mplus.scores.CharacterMythicPlusScoresData
import com.nemesis.rio.presentation.view.epoxy.EpoxyModelsBuilderDelegate

class CharacterMythicPlusDataController(
    private val scoresModelsBuilder: EpoxyModelsBuilderDelegate<CharacterMythicPlusScoresData?>,
    private val ranksModelsBuilder: EpoxyModelsBuilderDelegate<CharacterMythicPlusRanksData?>,
    private val runsModelsBuilder: EpoxyModelsBuilderDelegate<CharacterMythicPlusRunsData?>
) : TypedEpoxyController<CharacterMythicPlusData>() {

    override fun buildModels(data: CharacterMythicPlusData?) {
        if (data != null) {
            scoresModelsBuilder.buildModels(data.scoresData, this)
            ranksModelsBuilder.buildModels(data.ranksData, this)
            runsModelsBuilder.buildModels(data.runsData, this)
        } else {
            mythicPlusDataNotFoundMessage()
        }
    }

    private fun mythicPlusDataNotFoundMessage() {
        itemCenterText {
            id("no_mplus_data")
            textResId(R.string.character_mplus_data_not_found)
        }
    }
}
