package com.karim.albumapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karim.albumapp.ui.albums.AlbumsFragment

class AlbumsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.albums_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AlbumsFragment.newInstance())
                .commitNow()
        }
    }

}
