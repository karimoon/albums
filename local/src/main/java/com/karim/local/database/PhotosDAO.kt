package com.karim.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.karim.local.model.PhotoAlbumLocal
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface PhotosDAO {

    @Query("SELECT * FROM photos")
    fun getPhotos(): Observable<List<PhotoAlbumLocal>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPhotos(photos: List<PhotoAlbumLocal>): Single<List<Long>>

    @Query("DELETE FROM photos")
    fun clearCachedPhotos(): Completable

}