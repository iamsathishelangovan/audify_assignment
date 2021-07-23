package com.assignment.musicplayer.dagger

import com.assignment.musicplayer.PlayListActivity
import com.assignment.musicplayer.adapter.SongViewHolder
import com.assignment.musicplayer.viewmodel.PlayListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: PlayListActivity)

    fun inject(songViewHolder: SongViewHolder)

    fun inject(playListViewModel: PlayListViewModel)
}