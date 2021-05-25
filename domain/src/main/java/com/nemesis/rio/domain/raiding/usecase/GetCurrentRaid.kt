package com.nemesis.rio.domain.raiding.usecase

import com.nemesis.rio.domain.raiding.Raid

class GetCurrentRaid {

    operator fun invoke() = enumValues<Raid>().first()
}
