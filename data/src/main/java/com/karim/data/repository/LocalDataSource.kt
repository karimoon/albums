package com.karim.data.repository

import com.karim.data.models.PhotoAlbumData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface LocalDataSource {

    fun getPhotos(): Observable<List<PhotoAlbumData>>

    fun savePhotos(photos : List<PhotoAlbumData>): Single<List<Long>>

    fun clearPhotoData(): Completable
}