package com.nemesis.rio.domain.raiding.usecase

import com.nemesis.rio.domain.raiding.Raid

class GetCurrentRaid {
    private val currentRaid = enumValues<Raid>().first()

    operator fun invoke() = currentRaid

}
