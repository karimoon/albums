package com.karim.data.repository

import com.karim.data.models.PhotoAlbumData
import io.reactivex.Observable

interface RemoteDataSource {

    fun getPhotoAlbumsData() : Observable<List<PhotoAlbumData>>


}