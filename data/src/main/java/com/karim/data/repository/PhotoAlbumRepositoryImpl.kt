package com.karim.data.repository

import com.karim.data.mapper.Mapper
import com.karim.data.models.PhotoAlbumData
import com.karim.domain.models.PhotoAlbumEntity
import com.karim.domain.repository.PhotoAlbumsRepository
import io.reactivex.Observable

class PhotoAlbumRepositoryImpl constructor(
    private val photoAlbumMapper: Mapper<PhotoAlbumEntity, PhotoAlbumData>,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : PhotoAlbumsRepository {
    override fun getPhotoAlbumsData(): Observable<List<PhotoAlbumEntity>> {

        val localPhotos = localDataSource.getPhotos()
            .map { photos ->
                photos.map { photoAlbumMapper.from(it) }
            }

        return remoteDataSource.getPhotoAlbumsData()
            .map { photos ->
                localDataSource.clearPhotoData()
                localDataSource.savePhotos(photos)
                photos.map { photoAlbumMapper.from(it) }
            }.onErrorResumeNext(Observable.empty())
            .concatWith(localPhotos)

    }

}