package com.karim.albumapp

import android.app.Application
import com.karim.data.mapper.PhotoAlbumDomainDataMapper
import com.karim.data.models.PhotoAlbumData
import com.karim.data.repository.LocalDataSource
import com.karim.data.repository.PhotoAlbumRepositoryImpl
import com.karim.data.repository.RemoteDataSource
import com.karim.domain.models.PhotoAlbumEntity
import com.karim.domain.repository.PhotoAlbumsRepository
import com.karim.domain.usecases.GetPhotosAlbumsTask
import com.karim.local.database.PhotoAlbumDb
import com.karim.local.database.PhotosDAO
import com.karim.local.mapper.PhotoAlbumDataLocalMapper
import com.karim.local.model.PhotoAlbumLocal
import com.karim.local.source.LocalDataSourceImpl
import com.karim.presentation.mapper.PhotoAlbumEntityMapper
import com.karim.presentation.model.PhotoAlbum
import com.karim.presentation.sharedUiRepos.SharedUIRepo
import com.karim.presentation.viewmodels.AlbumsViewModel
import com.karim.presentation.viewmodels.DetailViewModel
import com.karim.remote.api.PhotoAlbumsRetrofitService
import com.karim.remote.mapper.Mapper
import com.karim.remote.mapper.PhotoAlbumDataNetworkMapper
import com.karim.remote.model.PhotoAlbumNetwork
import com.karim.remote.source.RemoteDataSourceImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class AlbumApplication : Application() {

    private val domainModule = module {

        single {
            GetPhotosAlbumsTask(get(),get(named("isIoScheduler")),
                get(named("isNotIoScheduler")))
        }

    }

    private val dataModule = module {

        single<com.karim.data.mapper.Mapper<PhotoAlbumEntity, PhotoAlbumData>> {

            PhotoAlbumDomainDataMapper()
        }

        single<PhotoAlbumsRepository>{
            PhotoAlbumRepositoryImpl(get(),get(),get())
        }

        single<RemoteDataSource> {
            RemoteDataSourceImpl(get(), get())
        }


        single<LocalDataSource> {
            LocalDataSourceImpl(get(), get())
        }

    }

    private val remoteModule = module {

        fun providesRetrofit() : Retrofit {
            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(com.karim.remote.BuildConfig.BASE_URL)
                .build()
        }

        single {
            providesRetrofit()
        }

        fun providesPhotoAlbumsService(retrofit: Retrofit): PhotoAlbumsRetrofitService {
            return retrofit.create(PhotoAlbumsRetrofitService::class.java)
        }

        single {
            providesPhotoAlbumsService(get())
        }


        single<Mapper<PhotoAlbumData, PhotoAlbumNetwork>>{
            PhotoAlbumDataNetworkMapper()
        }
    }

    private val localModule = module {

        single<com.karim.local.mapper.Mapper<PhotoAlbumData, PhotoAlbumLocal>>{
            PhotoAlbumDataLocalMapper()
        }

        fun providesDatabase(
            application: Application
        ) :PhotoAlbumDb {
            return PhotoAlbumDb.getInstance(application.applicationContext)
        }

        fun providesPhotosDao(
            photoAlbumDb: PhotoAlbumDb
        ): PhotosDAO {
            return photoAlbumDb.getPhotosDao()
        }


        single {
            providesPhotosDao(get())
        }

        single {
            providesDatabase(get())
        }
    }

    private val presentationModule = module {

        single<com.karim.presentation.mapper.Mapper<PhotoAlbumEntity, PhotoAlbum>> {
            PhotoAlbumEntityMapper()
        }

        viewModel { AlbumsViewModel(get() , get()) }

        viewModel { DetailViewModel() }


        single {
            SharedUIRepo()
        }
    }

    private val appModule = module {

        single(named("isIoScheduler"))  {
            Schedulers.io()
        }

        single(named("isNotIoScheduler")) {
            AndroidSchedulers.mainThread()
        }

    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AlbumApplication)
            modules(listOf(appModule,domainModule,
                dataModule,remoteModule,
                localModule,presentationModule))
        }
    }

}