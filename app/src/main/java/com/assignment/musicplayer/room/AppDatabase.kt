package com.assignment.musicplayer.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assignment.musicplayer.model.SongEntity

@Database(entities = [SongEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun songDao(): SongDao

}