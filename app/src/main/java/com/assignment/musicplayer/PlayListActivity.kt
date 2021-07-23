package com.assignment.musicplayer

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignment.musicplayer.adapter.SongListAdapter
import com.assignment.musicplayer.model.MediaStoreSong
import com.assignment.musicplayer.viewmodel.PlayListViewModel

class PlayListActivity : AppCompatActivity() {


    private val READ_EXTERNAL_STORAGE_REQUEST = 0x1045

    private val TAG = "PlayListActivity"

    private val viewModel: PlayListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.rv_song_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.songs.observe(this, Observer<List<MediaStoreSong>> { songs ->
            //Set in recycler view
            Log.d(TAG, "Songs List Size : " + songs.size)
            recyclerView.adapter = SongListAdapter(songs)

            viewModel.insertSongs()
        })

        openMediaStore()

    }

    private fun openMediaStore() {
        if (haveStoragePermission()) {
            showSongs()
        } else {
            requestPermission()
        }
    }

    /**
     * Convenience method to check if [Manifest.permission.READ_EXTERNAL_STORAGE] permission
     * has been granted to the app.
     */
    private fun haveStoragePermission() =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PERMISSION_GRANTED


    /**
     * Convenience method to request [Manifest.permission.READ_EXTERNAL_STORAGE] permission.
     */
    private fun requestPermission() {
        if (!haveStoragePermission()) {
            val permissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(this, permissions, READ_EXTERNAL_STORAGE_REQUEST)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    showSongs()
                } else {
                    // If we weren't granted the permission, check to see if we should show
                    // rationale for the permission.
                    val showRationale =
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )

                    /**
                     * If we should show the rationale for requesting storage permission, then
                     * we'll show [ActivityMainBinding.permissionRationaleView] which does this.
                     *
                     * If `showRationale` is false, this means the user has not only denied
                     * the permission, but they've clicked "Don't ask again". In this case
                     * we send the user to the settings page for the app so they can grant
                     * the permission (Yay!) or uninstall the app.
                     */
                    if (showRationale) {
                        showNoAccess()
                    } else {
                        goToSettings()
                    }
                }
                return
            }
        }
    }

    private fun showSongs() {
        viewModel.loadSongs()

    }

    private fun showNoAccess() {

    }

    private fun goToSettings() {
        Intent(ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName")).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.also { intent ->
            startActivity(intent)
        }
    }


}