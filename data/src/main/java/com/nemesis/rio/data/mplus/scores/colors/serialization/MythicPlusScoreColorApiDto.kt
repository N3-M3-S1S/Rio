package com.nemesis.rio.data.mplus.scores.colors.serialization

import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.scores.color.HexColor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MythicPlusScoreColorApiDto(
    val score: MythicPlusScore,
    @SerialName("rgbHex")
    val hexColor: HexColor
)
