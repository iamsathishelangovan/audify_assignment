package com.assignment.musicplayer.adapter

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.assignment.musicplayer.R
import com.assignment.musicplayer.SongApplication
import com.assignment.musicplayer.SongPlayerActivity
import com.assignment.musicplayer.model.MediaStoreSong
import com.assignment.musicplayer.room.SongDao
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textview.MaterialTextView
import javax.inject.Inject

class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var tvSongTitle: MaterialTextView
    lateinit var cbIsFavorite: MaterialCheckBox

    @Inject
    lateinit var songDao: SongDao

    init {
        initializeItems(itemView)
    }

    private fun initializeItems(itemView: View) {
        tvSongTitle = itemView.findViewById(R.id.tv_song_title)
        cbIsFavorite = itemView.findViewById(R.id.cb_is_favorite)
    }

    fun onBind(song: MediaStoreSong?) {

        tvSongTitle.text = song?.displayName
        song?.isFavorite?.let { isFavorite ->
            cbIsFavorite.isChecked = isFavorite
        }


        itemView.setOnClickListener {
            val intent = Intent(itemView.context, SongPlayerActivity::class.java)
            intent.putExtra("INTENT_EXTRA_SONG_URI", song?.contentUri.toString())
            intent.putExtra("INTENT_EXTRA_SONG_NAME", song?.displayName)
            itemView.context.startActivity(intent)
        }
    }

}