package com.assignment.musicplayer.viewmodel

import android.app.Application
import android.content.ContentUris
import android.database.ContentObserver
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.assignment.musicplayer.model.MediaStoreSong
import com.assignment.musicplayer.model.SongEntity
import com.assignment.musicplayer.room.SongDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlayListViewModel(application: Application) : AndroidViewModel(application) {

    private val songList = MutableLiveData<List<MediaStoreSong>>()
    private var songEntityList: List<SongEntity> = ArrayList()

    val songs: LiveData<List<MediaStoreSong>> get() = songList

    private val TAG = "PlayListViewModel"

    @Inject
    lateinit var songDao: SongDao

    fun loadSongs() {
        viewModelScope.launch {
            val imageList = querySongs()
            songList.postValue(imageList)
        }
    }

    fun insertSongs() {
        viewModelScope.launch {
//            songDao.insertAll(songEntityList)
        }
    }


    private suspend fun querySongs(): List<MediaStoreSong> {
        val songs = mutableListOf<MediaStoreSong>()


        withContext(Dispatchers.IO) {

            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
            )

            getApplication<Application>().contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
            )?.use { cursor ->

                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val displayNameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)

                Log.i(TAG, "Found ${cursor.count} songs")

                while (cursor.moveToNext()) {

                    // Here we'll use the column indexs that we found above.
                    val id = cursor.getLong(idColumn)
                    val displayName = cursor.getString(displayNameColumn)
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                    val song = MediaStoreSong(id, displayName, false, contentUri)
                    songs += song

                    songEntityList + SongEntity(id, id, isFavorite = false)

                }
            }
        }

        Log.v(TAG, "Found ${songs.size} songs")
        return songs
    }

}