package com.assignment.musicplayer.room

import androidx.room.*
import com.assignment.musicplayer.model.SongEntity

@Dao
interface SongDao {

    @Insert
    suspend fun insertAll(song: List<SongEntity>)

    @Insert
    suspend fun insert(song: SongEntity)

    @Query("SELECT isFavorite FROM songs_metadata WHERE mediaStoreId == :songId")
    suspend fun isFavourite(songId: Int) : Boolean

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavoriteStatus(song: SongEntity)

}