package com.karim.domain.usecases

import com.karim.domain.models.PhotoAlbumEntity
import com.karim.domain.repository.PhotoAlbumsRepository
import com.karim.domain.usecases.base.ObservableUseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.internal.schedulers.IoScheduler

class GetPhotosAlbumsTask constructor(
    private val photoAlbumsRepository: PhotoAlbumsRepository,
    backgroundScheduler: Scheduler,
    foregroundScheduler: Scheduler
) : ObservableUseCase<List<PhotoAlbumEntity>>(
    backgroundScheduler,
    foregroundScheduler
) {
    override fun generateObservable(): Observable<List<PhotoAlbumEntity>> {
        return photoAlbumsRepository.getPhotoAlbumsData()
    }

}