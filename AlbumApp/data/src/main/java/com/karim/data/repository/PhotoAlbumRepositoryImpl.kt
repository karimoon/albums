package com.karim.data.repository

import com.karim.data.mapper.Mapper
import com.karim.data.models.PhotoAlbumData
import com.karim.domain.models.PhotoAlbumEntity
import com.karim.domain.repository.PhotoAlbumsRepository
import io.reactivex.Observable

class PhotoAlbumRepositoryImpl constructor(
    private val photoAlbumMapper : Mapper<PhotoAlbumEntity, PhotoAlbumData>,
    private val remoteDataSource: RemoteDataSource
) : PhotoAlbumsRepository {
    override fun getPhotoAlbumsData(): Observable<List<PhotoAlbumEntity>> {

        return remoteDataSource.getPhotoAlbumsData()
            .map { photos ->
                //localDataSource.savePhotos(photos)
                //println("photos" +photos.toString())
                photos.map { photoAlbumMapper.from(it) }
            }
    }


}