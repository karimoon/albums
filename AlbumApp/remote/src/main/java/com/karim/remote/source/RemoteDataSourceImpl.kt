package com.karim.remote.source

import com.karim.data.models.PhotoAlbumData
import com.karim.remote.api.PhotoAlbumsRetrofitService
import com.karim.data.repository.RemoteDataSource
import com.karim.remote.mapper.Mapper
import com.karim.remote.model.PhotoAlbumNetwork
import io.reactivex.Observable

class RemoteDataSourceImpl constructor(
    private val photoAlbumMapper: Mapper<PhotoAlbumData, PhotoAlbumNetwork>,
    private val photoAlbumsService: PhotoAlbumsRetrofitService
) : RemoteDataSource {

    override fun getPhotoAlbumsData():
            Observable<List<PhotoAlbumData>> {
        return photoAlbumsService.getPhotoAlbumsData()
            .map { response ->
                //println("Remote get photos Invoked")
                response.map { photoAlbum: PhotoAlbumNetwork ->
                    photoAlbumMapper.from(photoAlbum)
                }
            }
    }

}