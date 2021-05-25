package com.nemesis.rio.presentation.profile.character

import android.os.Parcel
import android.os.Parcelable
import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.domain.profile.character.attributes.CharacterAttributes
import com.nemesis.rio.domain.profile.character.attributes.Covenant
import com.nemesis.rio.domain.profile.character.gear.Gear
import com.nemesis.rio.presentation.profile.ProfileCommonDataParcel
import com.nemesis.rio.presentation.utils.extensions.readEnum
import com.nemesis.rio.presentation.utils.extensions.readEnumOrNull
import com.nemesis.rio.presentation.utils.extensions.readStringOrThrow
import com.nemesis.rio.presentation.utils.extensions.writeEnum
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler

@Parcelize
@TypeParceler<Character, CharacterParceler>
data class CharacterParcel(val character: Character) : Parcelable

private object CharacterParceler : Parceler<Character> {
    override fun create(parcel: Parcel) = with(parcel) {
        val (name, region, faction, url) = ProfileCommonDataParcel.readFromParcelOrThrow(this)
        Character(
            name,
            region,
            faction,
            url,
            readStringOrThrow(),
            readAttributes(),
            readGear(),
            readStringOrThrow(),
            readString()
        )
    }

    override fun Character.write(parcel: Parcel, flags: Int) {
        ProfileCommonDataParcel.writeToParcel(parcel, this)
        with(parcel) {
            writeString(realm)
            writeAttributes(attributes)
            writeGear(gear)
            writeString(imageUrl)
            writeString(guildName)
        }
    }

    private fun Parcel.writeAttributes(characterAttributes: CharacterAttributes) {
        with(characterAttributes) {
            writeEnum(characterClass)
            writeEnum(activeSpec)
            writeEnum(race)
            writeEnum(covenant)
        }
    }

    private fun Parcel.readAttributes() =
        CharacterAttributes(
            readEnum(),
            readEnum(),
            readEnum(),
            readEnumOrNull<Covenant>(),
        )

    private fun Parcel.writeGear(gear: Gear) {
        writeInt(gear.itemLevel)
    }

    private fun Parcel.readGear() = Gear(readInt())
}
