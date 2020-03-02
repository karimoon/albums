package com.karim.local.database

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.karim.local.model.PhotoAlbumLocal

@Database(
    entities = [PhotoAlbumLocal::class],
    version = 1,
    exportSchema = false
)
abstract class PhotoAlbumDb : RoomDatabase() {

    companion object {
        private val LOCK = Any()
        private const val DATABASE_NAME = "album_photos.db"
        @Volatile
        private var INSTANCE: PhotoAlbumDb? = null

        fun getInstance(@NonNull context: Context): PhotoAlbumDb {
            if (INSTANCE == null) {
                synchronized(LOCK) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            PhotoAlbumDb::class.java,
                            DATABASE_NAME
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    abstract fun getPhotosDao(): PhotosDAO
}