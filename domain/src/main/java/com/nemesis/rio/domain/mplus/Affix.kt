package com.nemesis.rio.domain.mplus

enum class Affix(val tier: Tier) {
    TYRANNICAL(Tier.T2), FORTIFIED(Tier.T2),

    BOLSTERING(Tier.T4), RAGING(Tier.T4), SANGUINE(Tier.T4),
    BURSTING(Tier.T4), INSPIRING(Tier.T4), SPITEFUL(Tier.T4),

    NECROTIC(Tier.T7), EXPLOSIVE(Tier.T7), QUAKING(Tier.T7),
    VOLCANIC(Tier.T7), GRIEVOUS(Tier.T7), STORMING(Tier.T7),

    PRIDEFUL(Tier.T10), TORMENTED(Tier.T10), ENCRYPTED(Tier.T10);

    enum class Tier {
        T2, T4, T7, T10
    }
}
