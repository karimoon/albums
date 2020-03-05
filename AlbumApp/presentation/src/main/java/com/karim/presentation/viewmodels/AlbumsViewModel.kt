package com.karim.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.karim.domain.models.PhotoAlbumEntity
import com.karim.domain.usecases.GetPhotosAlbumsTask
import com.karim.presentation.mapper.Mapper
import com.karim.presentation.model.PhotoAlbum
import com.karim.presentation.model.Resource
import com.karim.presentation.model.Status
import io.reactivex.BackpressureStrategy
import io.reactivex.functions.Function
import io.reactivex.Observable


class AlbumsViewModel constructor(
    private val photoAlbumEntityMapper : Mapper<PhotoAlbumEntity, PhotoAlbum>,
    getPhotosAlbumsTask: GetPhotosAlbumsTask
) : ViewModel() {

    private val photosMediator = MediatorLiveData<Resource<List<PhotoAlbum>>>()

    val photoAlbumListSource: LiveData<Resource<List<PhotoAlbum>>>
        get() = photosMediator

    val photosAlbumResource = getPhotosAlbumsTask
        .buildUseCase()
        .map { photoAlbumEntities ->
            photoAlbumEntities.map {
                photoAlbumEntityMapper.to(it)
            }
        }.map { Resource.success(it) }
        .startWith(Resource.loading())
        .onErrorResumeNext(Function {
            it.localizedMessage?.let {
                Observable.just(Resource.error(it))
            }
        }).toFlowable(BackpressureStrategy.LATEST)
        .toLiveData()

    init {

        photosMediator.addSource(photosAlbumResource) {
            photosMediator.value = it
            if (it.status != Status.LOADING) {
                photosMediator.removeSource(photosAlbumResource)
            }
        }
    }
}
