package com.karim.albumapp

import android.app.Application
import com.karim.data.mapper.PhotoAlbumDomainDataMapper
import com.karim.data.models.PhotoAlbumData
import com.karim.data.repository.PhotoAlbumRepositoryImpl
import com.karim.data.repository.RemoteDataSource
import com.karim.domain.models.PhotoAlbumEntity
import com.karim.domain.repository.PhotoAlbumsRepository
import com.karim.domain.usecases.GetPhotosAlbumsTask
import com.karim.presentation.mapper.PhotoAlbumEntityMapper
import com.karim.presentation.model.PhotoAlbum
import com.karim.presentation.viewmodels.AlbumsViewModel
import com.karim.remote.api.PhotoAlbumsService
import com.karim.remote.mapper.Mapper
import com.karim.remote.mapper.PhotoAlbumDataNetworkMapper
import com.karim.remote.model.PhotoAlbumNetwork
import com.karim.remote.source.RemoteDataSourceImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class AlbumApplication : Application() {

    private val appModule = module {


        single {
            PhotoAlbumsService.createService()
        }

        single<Mapper<PhotoAlbumData, PhotoAlbumNetwork>>{
            PhotoAlbumDataNetworkMapper()
        }

        single<com.karim.data.mapper.Mapper<PhotoAlbumEntity, PhotoAlbumData>> {

            PhotoAlbumDomainDataMapper()
        }

        single<PhotoAlbumsRepository>{
            PhotoAlbumRepositoryImpl(get(),get())
        }

        single<RemoteDataSource> {
            RemoteDataSourceImpl(get(), get())
        }

        single {
            GetPhotosAlbumsTask(get(),get(), get())
        }

        single<com.karim.presentation.mapper.Mapper<PhotoAlbumEntity, PhotoAlbum>> {
            PhotoAlbumEntityMapper()
        }

        viewModel { AlbumsViewModel(get() , get()) }


        single{
            Schedulers.io() as IoScheduler
        }

        single{
            AndroidSchedulers.mainThread()
        }

    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AlbumApplication)
            modules(appModule)
        }
    }

}