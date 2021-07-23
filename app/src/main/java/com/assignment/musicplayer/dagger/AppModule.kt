package com.assignment.musicplayer.dagger

import android.content.Context
import androidx.room.Room
import com.assignment.musicplayer.room.AppDatabase
import com.assignment.musicplayer.room.SongDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val applicationContext: Context) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return applicationContext
    }

    @Provides
    @Singleton
    fun roomDatabase(): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "song_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providesRoomDao(appDatabase: AppDatabase): SongDao {
        return appDatabase.songDao()
    }
}