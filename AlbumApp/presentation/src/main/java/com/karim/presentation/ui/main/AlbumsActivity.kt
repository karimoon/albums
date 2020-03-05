package com.karim.presentation.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karim.presentation.R

class AlbumsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.albums_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,
                    AlbumsFragment.newInstance()
                )
                .commitNow()
        }
    }

}
