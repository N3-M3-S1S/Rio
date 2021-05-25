package sharedTest

import com.nemesis.rio.domain.profile.Faction
import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.domain.profile.character.attributes.*
import com.nemesis.rio.domain.profile.character.gear.Gear
import com.nemesis.rio.domain.profile.guild.Guild
import com.nemesis.rio.domain.server.Region

fun createTestCharacter(name: String = "testCharacter"): Character {
    val region = Region.EU
    val realm = "testRealm"
    val faction = Faction.HORDE
    val url = "character_url"
    val attributes = CharacterAttributes(
        CharacterClass.MONK,
        Spec.WINDWALKER,
        Race.PANDAREN,
        Covenant.NECROLORD
    )
    val gear = Gear(123)
    return Character(name, region, faction, url, realm, attributes, gear, "imageUrl", "guild")
}

fun createTestGuild(name: String = "testGuild"): Guild {
    val region = Region.US
    val realm = "testRealm"
    val faction = Faction.ALLIANCE
    val url = "guild_url"
    return Guild(name, region, faction, url, realm)
}
