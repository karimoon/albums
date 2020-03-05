package com.karim.remote.source

import com.karim.data.repository.RemoteDataSource
import com.karim.remote.api.PhotoAlbumsRetrofitService
import com.karim.remote.mapper.PhotoAlbumDataNetworkMapper
import com.karim.remote.model.PhotoAlbumNetwork
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RemoteDataSourceImplTest {

    private lateinit var remoteDataSourceImpl : RemoteDataSource

    private val photoAlbumMapper = PhotoAlbumDataNetworkMapper()

    @Mock
    private lateinit var photoAlbumsService: PhotoAlbumsRetrofitService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        remoteDataSourceImpl = RemoteDataSourceImpl(
            photoAlbumMapper,
            photoAlbumsService
        )

    }

    @Test
    fun getPhotoAlbumsData_success() {

        //Arrange
        val photo1 = PhotoAlbumNetwork(1,3,"title of photo 1",
            "url_photo1", "thumbnailUrl_photo1")
        val photo2 = PhotoAlbumNetwork(2,3,"title of photo 2",
            "url_photo2", "thumbnailUrl_photo2")

        val listPhotos = listOf(photo1,photo2)

        //Act

        Mockito.`when`(photoAlbumsService.getPhotoAlbumsData())
            .thenReturn(Observable.just(listPhotos))

        val testObservable = remoteDataSourceImpl.getPhotoAlbumsData().test()

        //Assert
        Mockito.verify(photoAlbumsService).getPhotoAlbumsData()

        testObservable.assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == listPhotos.map {
                    photoAlbumMapper.from(it)
                }
            }
            .assertComplete()


    }
}