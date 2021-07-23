package com.assignment.musicplayer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class SongPlayerViewModel(application: Application) : AndroidViewModel(application) {

    var song_uri = "content://media/external/audio/media/17381"

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    fun getPlayWhenReady() = playWhenReady
    fun getCurrentWindow() = currentWindow
    fun getPlaybackPosition() = playbackPosition

    fun setPlayWhenReady(playWhenReady: Boolean) {
        this.playWhenReady = playWhenReady
    }

    fun setCurrentWindow(currentWindow: Int) {
        this.currentWindow = currentWindow
    }

    fun setPlaybackPosition(playbackPosition: Long) {
        this.playbackPosition = playbackPosition
    }

}