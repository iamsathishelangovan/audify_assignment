package com.assignment.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.assignment.musicplayer.viewmodel.SongPlayerViewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util
import com.google.android.material.textview.MaterialTextView

class SongPlayerActivity : AppCompatActivity() {

    var audioPlayer: SimpleExoPlayer? = null
    private lateinit var playerView: PlayerView
    private lateinit var tvSongTitle: MaterialTextView

    private var songUri = "content://media/external/audio/media/17381"
    private var songName = ""

    private val viewModel: SongPlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_player)

        playerView = findViewById(R.id.pv_audio_player)
        tvSongTitle = findViewById(R.id.tv_song_title)
        if (intent.hasExtra("INTENT_EXTRA_SONG_URI")) {
            songUri = intent.getStringExtra("INTENT_EXTRA_SONG_URI")!!

        }
        if (intent.hasExtra("INTENT_EXTRA_SONG_NAME")) {
            songName = intent.getStringExtra("INTENT_EXTRA_SONG_NAME")!!
            tvSongTitle.text = songName
        }

    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if ((Util.SDK_INT < 24 || audioPlayer == null)) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private fun initializePlayer() {
        audioPlayer = SimpleExoPlayer.Builder(this)
            .build()
            .also { exoPlayer ->
                playerView.player = exoPlayer

                val mediaItem = MediaItem.fromUri(songUri)
                exoPlayer.setMediaItem(mediaItem)

                exoPlayer.playWhenReady = viewModel.getPlayWhenReady()
                exoPlayer.seekTo(viewModel.getCurrentWindow(), viewModel.getPlaybackPosition())
                exoPlayer.prepare()
                exoPlayer.play()
            }
    }

    private fun releasePlayer() {
        audioPlayer?.run {
            viewModel.setPlaybackPosition(this.currentPosition)
            viewModel.setCurrentWindow(this.currentWindowIndex)
            viewModel.setPlayWhenReady(this.playWhenReady)
            release()
        }
        audioPlayer = null
    }
}