package com.assignment.musicplayer

import android.app.Application
import com.assignment.musicplayer.dagger.AppComponent
import com.assignment.musicplayer.dagger.AppModule
import com.assignment.musicplayer.dagger.DaggerAppComponent

class SongApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }

    companion object {
        private lateinit var instance: SongApplication
        fun getInstance(): SongApplication {
            return instance
        }
    }

    fun appComponent(): AppComponent {
        return appComponent
    }


}