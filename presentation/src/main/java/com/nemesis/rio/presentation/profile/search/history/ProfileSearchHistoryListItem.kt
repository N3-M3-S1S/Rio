package com.nemesis.rio.presentation.profile.search.history

import kotlinx.datetime.Instant

data class ProfileSearchHistoryListItem(
    val id: Long,
    val name: String,
    val profileDescription: CharSequence,
    val serverInfo: CharSequence,
    val lastSearchDateTime: Instant,
    val onClick: () -> Unit,
    val onSwipe: () -> Unit
)
