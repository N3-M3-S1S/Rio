package com.nemesis.rio.presentation.profile.search.history

import kotlinx.datetime.LocalDateTime

data class ProfileSearchHistoryListItem(
    val id: Long,
    val name: String,
    val profileDescription: CharSequence,
    val serverInfo: CharSequence,
    val lastSearchDateTime: LocalDateTime,
    val onClick: () -> Unit,
    val onSwipe: () -> Unit
)
