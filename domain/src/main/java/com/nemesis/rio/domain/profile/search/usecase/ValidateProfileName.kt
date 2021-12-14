package com.nemesis.rio.domain.profile.search.usecase

class ValidateProfileName {

    operator fun invoke(profileName: String): Boolean =
        profileName.length >= 2 && profileName.isNotBlank()

}