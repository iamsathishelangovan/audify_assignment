package com.assignment.musicplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.musicplayer.R
import com.assignment.musicplayer.model.MediaStoreSong

class SongListAdapter(val songList: List<MediaStoreSong>) : RecyclerView.Adapter<SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_song, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.onBind(songList[position])
    }

    override fun getItemCount(): Int {
        return songList.size
    }

}