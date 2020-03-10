package com.karim.local.source

import com.karim.data.models.PhotoAlbumData
import com.karim.data.repository.LocalDataSource
import com.karim.local.database.PhotosDAO
import com.karim.local.mapper.Mapper
import com.karim.local.model.PhotoAlbumLocal
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class LocalDataSourceImpl constructor(
    private val photosDAO: PhotosDAO,
    private val photoAlbumDataLocalMapper: Mapper<PhotoAlbumData, PhotoAlbumLocal>
) : LocalDataSource {


    override fun getPhotos(): Observable<List<PhotoAlbumData>> {
        return photosDAO.getPhotos()
            .map {
                photosLocal -> photosLocal.map {
                photoAlbumDataLocalMapper.from(it)
            }
            }
    }

    override fun savePhotosObservable(photos : List<PhotoAlbumData>): Single<List<Long>> {
        return photosDAO.addPhotos(
            photos.map {
                photoAlbumDataLocalMapper.to(it)
            }
        )
    }


    override fun clearPhotoDataObservable(): Completable {
       return photosDAO.clearCachedPhotos()
    }

    override fun savePhotos(photos: List<PhotoAlbumData>) {
        savePhotosObservable(photos).blockingGet()
    }

    override fun clearPhotoData() {
        clearPhotoDataObservable().blockingGet()
    }

}