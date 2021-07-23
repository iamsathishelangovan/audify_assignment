package com.assignment.musicplayer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs_metadata")
data class SongEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long,
    @ColumnInfo(name = "mediaStoreId") var mediaStoreId: Long,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean
)