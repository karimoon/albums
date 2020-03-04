package com.karim.local.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

open class PhotoAlbumDbTest {

    private var photoAlbumDb: PhotoAlbumDb? = null

    @Before
    fun setUp() {
        photoAlbumDb = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PhotoAlbumDb::class.java!!
        ).build()
    }

    fun getPhotosDao(): PhotosDAO {
        return photoAlbumDb!!.getPhotosDao()
    }

    @After
    fun finish() {
        photoAlbumDb!!.close()
    }

}