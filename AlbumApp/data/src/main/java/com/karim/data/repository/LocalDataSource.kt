package com.karim.data.repository

import com.karim.data.models.PhotoAlbumData
import io.reactivex.Observable

interface LocalDataSource {

    fun getPhotos(): Observable<List<PhotoAlbumData>>

    fun savePhotos(photos : List<PhotoAlbumData>)

    fun clearPhotoData()
}