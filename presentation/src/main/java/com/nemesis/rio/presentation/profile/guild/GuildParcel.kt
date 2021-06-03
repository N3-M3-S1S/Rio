package com.nemesis.rio.presentation.profile.guild

import android.os.Parcel
import android.os.Parcelable
import com.nemesis.rio.domain.profile.Guild
import com.nemesis.rio.presentation.profile.ProfileCommonDataParcel
import com.nemesis.rio.presentation.utils.extensions.readStringOrThrow
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler

@Parcelize
@TypeParceler<Guild, GuildParceler>
data class GuildParcel(val guild: Guild) : Parcelable

private object GuildParceler : Parceler<Guild> {
    override fun create(parcel: Parcel) = with(parcel) {
        val (name, serverInfo, faction, url) = ProfileCommonDataParcel.readFromParcelOrThrow(this)
        Guild(name, serverInfo, faction, url, readStringOrThrow())
    }

    override fun Guild.write(parcel: Parcel, flags: Int) {
        ProfileCommonDataParcel.writeToParcel(parcel, this)
        parcel.writeString(realm)
    }
}
