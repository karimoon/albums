package com.karim.domain.repository

import com.karim.domain.models.PhotoAlbumEntity
import io.reactivex.Observable

interface PhotoAlbumsRepository {

    fun getPhotoAlbumsData() : Observable<List<PhotoAlbumEntity>>
}