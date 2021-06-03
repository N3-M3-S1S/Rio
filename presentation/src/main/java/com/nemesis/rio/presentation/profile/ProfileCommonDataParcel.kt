package com.nemesis.rio.presentation.profile

import android.os.Parcel
import android.os.Parcelable
import com.nemesis.rio.domain.profile.Faction
import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.presentation.utils.extensions.readParcelableOrThrow
import com.nemesis.rio.presentation.utils.extensions.writeParcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileCommonDataParcel(
    val name: String,
    val region: Region,
    val faction: Faction,
    val url: String
) : Parcelable {

    companion object {
        fun readFromParcelOrThrow(parcel: Parcel) =
            parcel.readParcelableOrThrow<ProfileCommonDataParcel>()

        fun writeToParcel(parcel: Parcel, profile: Profile) {
            parcel.writeParcelable(profile.toCommonDataParcel())
        }

        private fun Profile.toCommonDataParcel() =
            ProfileCommonDataParcel(name, region, faction, url)
    }
}


