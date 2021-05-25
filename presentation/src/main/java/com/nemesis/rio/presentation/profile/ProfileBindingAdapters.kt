package com.nemesis.rio.presentation.profile

import android.widget.TextView
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.databinding.BindingAdapter
import com.nemesis.rio.domain.profile.Faction
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm
import com.nemesis.rio.presentation.server.region.stringResId
import splitties.resources.appColor
import splitties.resources.appStr

@BindingAdapter("serverInfo_region", "serverInfo_realm", "serverInfo_faction")
fun TextView.setProfileRegionRealmAndFaction(
    region: Region?,
    realm: Realm?,
    faction: Faction?
) {
    if (region != null && realm != null && faction != null) {
        text = buildServerInfoString(region, realm, faction)
    }
}

fun buildServerInfoString(
    region: Region,
    realm: Realm,
    faction: Faction
) = buildSpannedString {
    val regionName = appStr(region.stringResId)
    append("$regionName-$realm ")
    val factionColor = appColor(faction.colorResId)
    color(factionColor) {
        val factionName = appStr(faction.stringResId)
        append(factionName)
    }
}
