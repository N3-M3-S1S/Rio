package com.nemesis.rio.data.profile.character.database

import androidx.room.*
import com.nemesis.rio.data.profile.character.database.attributes.CharacterAttributesEntity
import com.nemesis.rio.data.profile.character.database.attributes.toEntity
import com.nemesis.rio.data.profile.character.database.gear.GearEntity
import com.nemesis.rio.data.profile.character.database.gear.toEntity
import com.nemesis.rio.data.profile.database.ProfileDao
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.profile.search.ProfileSearchHistoryItem
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm
import com.nemesis.rio.utils.now
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

@Dao
abstract class CharacterDao : ProfileDao<Character>() {
    override val tableName = CharacterEntity.TABLE_NAME

    @Transaction
    override suspend fun saveOrUpdate(profile: Character): Long {
        var characterID = getProfileID(profile)
        if (characterID == null) {
            characterID = save(profile.toEntity())
        } else {
            update(profile.toUpdatePojo(characterID))
        }

        saveOrUpdateAttributesEntity(profile.attributes.toEntity(characterID))
        saveOrUpdateGearEntity(profile.gear.toEntity(characterID))
        return characterID
    }

    override suspend fun getProfileID(profile: Character): Long? =
        with(profile) { getCharacterID(name, region, realm) }

    @Query("SELECT profileID FROM CharacterEntity WHERE lower(name) = lower(:name) AND region = :region AND realm = :realm")
    protected abstract suspend fun getCharacterID(
        name: String,
        region: Region,
        realm: Realm
    ): Long?

    suspend fun searchCharacter(name: String, region: Region, realm: Realm) =
        searchCharacterPojo(name, region, realm)?.toCharacter()

    override fun getProfilesWithSearchHistory() = getCharacterPojosWithSearchHistory().map {
        it.map { characterPojo ->
            ProfileSearchHistoryItem(
                characterPojo.toCharacter(),
                characterPojo.characterEntity.lastSearchDateTime ?: LocalDateTime.now()
            )
        }
    }

    @Insert
    protected abstract suspend fun save(characterEntity: CharacterEntity): Long

    @Update(entity = CharacterEntity::class)
    protected abstract suspend fun update(characterUpdatePojo: CharacterUpdatePojo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun saveOrUpdateAttributesEntity(entity: CharacterAttributesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun saveOrUpdateGearEntity(entity: GearEntity)

    @Query("SELECT * FROM CharacterEntity WHERE lastSearchDateTime NOT NULL")
    protected abstract fun getCharacterPojosWithSearchHistory(): Flow<List<CharacterPojo>>

    @Query("SELECT * FROM CharacterEntity WHERE lower(name) = lower(:name) AND region = :region AND realm = :realm")
    protected abstract suspend fun searchCharacterPojo(
        name: String,
        region: Region,
        realm: Realm
    ): CharacterPojo?
}
