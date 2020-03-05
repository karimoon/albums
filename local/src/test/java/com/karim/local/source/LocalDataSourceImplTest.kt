package com.karim.local.source

import com.karim.data.models.PhotoAlbumData
import com.karim.data.repository.LocalDataSource
import com.karim.local.database.PhotosDAO
import com.karim.local.mapper.PhotoAlbumDataLocalMapper
import com.karim.local.model.PhotoAlbumLocal
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class LocalDataSourceImplTest {

    private lateinit var localDataSourceImpl : LocalDataSource

    private val photoAlbumDataLocalMapper = PhotoAlbumDataLocalMapper()


    @Mock
    private lateinit var photosDAO: PhotosDAO


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        localDataSourceImpl = LocalDataSourceImpl(photosDAO,photoAlbumDataLocalMapper)

    }

    @Test
    fun getPhotos_success() {

        //Arrange
        val photo1 = PhotoAlbumLocal(1,3,"title of photo 1",
            "url_photo1", "thumbnailUrl_photo1")
        val photo2 = PhotoAlbumLocal(2,3,"title of photo 2",
            "url_photo2", "thumbnailUrl_photo2")

        val listPhotos = listOf(photo1,photo2)

        val listPhotosData = listPhotos.map {
            photoAlbumDataLocalMapper.from(it)
        }

        //Act
        Mockito.`when`(photosDAO.getPhotos())
            .thenReturn(Observable.just(listPhotos))

        val testObserver = localDataSourceImpl.getPhotos().test()

        //Assert
        Mockito.verify(photosDAO).getPhotos()

        testObserver.assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == listPhotosData
            }
            .assertComplete()
    }

    @Test
    fun savePhotos_success() {
        //Arrange
        val photo1 = PhotoAlbumData(1,3,"title of photo 1",
            "url_photo1", "thumbnailUrl_photo1")
        val photo2 = PhotoAlbumData(2,3,"title of photo 2",
            "url_photo2", "thumbnailUrl_photo2")
        val photo3 = PhotoAlbumData(3,3,"title of photo 3",
            "url_photo3", "thumbnailUrl_photo3")

        val photoLocal1 = PhotoAlbumLocal(1,3,"title of photo 1",
            "url_photo1", "thumbnailUrl_photo1")
        val photoLocal2 = PhotoAlbumLocal(2,3,"title of photo 2",
            "url_photo2", "thumbnailUrl_photo2")
        val photoLocal3 = PhotoAlbumLocal(3,3,"title of photo 3",
            "url_photo3", "thumbnailUrl_photo3")

        val listPhotos = listOf(photo1,photo2,photo3)

        val listPhotosLocals = listOf(photoLocal1,photoLocal2,photoLocal3)

        //Act
        Mockito.`when`(photosDAO.addPhotos(listPhotosLocals))
            .thenReturn(Single.just(listOf(1L,2L,3L)))

        val testObserver = localDataSourceImpl.savePhotos(listPhotos).test()

        //Assert
        Mockito.verify(photosDAO).addPhotos(listPhotosLocals)

        testObserver.assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == listOf(1L,2L,3L)
            }
            .assertComplete()
    }

    @Test
    fun clearPhotoData_success() {

        //Act
        Mockito.`when`(photosDAO.clearCachedPhotos())
            .thenReturn(Completable.complete())

        val testObserver = localDataSourceImpl.clearPhotoData().test()


        //Assert
        Mockito.verify(photosDAO).clearCachedPhotos()

        testObserver.assertSubscribed()
            .assertComplete()

    }
}