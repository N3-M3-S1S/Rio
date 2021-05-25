package com.nemesis.rio.data.api.search

private const val NOT_FOUND_IN_API_ERROR_MESSAGE = "%s not found in API"

internal fun characterNotFoundInAPI(): Nothing = error(NOT_FOUND_IN_API_ERROR_MESSAGE.format("Character"))

internal fun guildNotFoundInAPI(): Nothing = error(NOT_FOUND_IN_API_ERROR_MESSAGE.format("Guild"))
