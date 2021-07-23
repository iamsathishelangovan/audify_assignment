package com.assignment.musicplayer.model

import android.net.Uri

data class MediaStoreSong(
    var mediaStoreId: Long?,
    var displayName: String?,
    var isFavorite: Boolean?,
    var contentUri: Uri?
)


