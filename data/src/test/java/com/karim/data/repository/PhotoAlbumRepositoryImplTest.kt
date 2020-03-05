package com.karim.data.repository

import com.karim.data.mapper.PhotoAlbumDomainDataMapper
import com.karim.data.models.PhotoAlbumData
import com.karim.domain.models.PhotoAlbumEntity
import com.karim.domain.repository.PhotoAlbumsRepository
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

class PhotoAlbumRepositoryImplTest {

    private lateinit var photoAlbumRepositoryImpl : PhotoAlbumsRepository

    private val photoAlbumMapper = PhotoAlbumDomainDataMapper()

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        photoAlbumRepositoryImpl = PhotoAlbumRepositoryImpl(
            photoAlbumMapper,
            remoteDataSource,
            localDataSource
        )

    }

    @Test
    fun getPhotoAlbumsData_success() {

        //Arrange
        val photo1 = PhotoAlbumData(1,3,"title of photo 1",
            "url_photo1", "thumbnailUrl_photo1")
        val photo2 = PhotoAlbumData(2,3,"title of photo 2",
            "url_photo2", "thumbnailUrl_photo2")

        val listPhotos = listOf(photo1,photo2)

        val listPhotosEntities = listPhotos.map {
            photoAlbumMapper.from(it)
        }

        //Act
        Mockito.`when`(localDataSource.getPhotos())
            .thenReturn(Observable.just(listPhotos))


        Mockito.`when`(remoteDataSource.getPhotoAlbumsData())
            .thenReturn(Observable.just(listPhotos))


        val testObserver = photoAlbumRepositoryImpl.getPhotoAlbumsData()
            .test()

        //Assert
        Mockito.verify(localDataSource).clearPhotoData()

        Mockito.verify(localDataSource).savePhotos(listPhotos)

        testObserver.assertSubscribed()
            .assertValueCount(2)
            .assertValues(listPhotosEntities, listPhotosEntities)
            .assertComplete()

    }

    @Test
    fun getPhotoAlbumsData_error(){

        //Arrange

        val errorMessage = "403 Access denied"

        val photo1 = PhotoAlbumData(1,3,"title of photo 1",
            "url_photo1", "thumbnailUrl_photo1")
        val photo2 = PhotoAlbumData(2,3,"title of photo 2",
            "url_photo2", "thumbnailUrl_photo2")

        val listPhotos = listOf(photo1,photo2)

        val listPhotosEntities = listPhotos.map {
            photoAlbumMapper.from(it)
        }

        //Act
        Mockito.`when`(localDataSource.getPhotos())
            .thenReturn(Observable.just(listPhotos))


        Mockito.`when`(remoteDataSource.getPhotoAlbumsData())
            .thenReturn(Observable.error(Throwable(errorMessage)))


        val testObserver = photoAlbumRepositoryImpl.getPhotoAlbumsData()
            .test()

        //Assert
        Mockito.verify(localDataSource ,
            times(0)).clearPhotoData()

        Mockito.verify(localDataSource,
            times(0)).savePhotos(listPhotos)

        testObserver.assertSubscribed()
            .assertValueCount(1)
            .assertValues(listPhotosEntities)
            .assertComplete()

    }
}