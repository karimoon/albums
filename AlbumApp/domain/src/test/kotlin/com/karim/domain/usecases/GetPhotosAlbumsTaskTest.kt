package com.karim.domain.usecases

import com.karim.domain.models.PhotoAlbumEntity
import com.karim.domain.repository.PhotoAlbumsRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.internal.schedulers.IoScheduler
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetPhotosAlbumsTaskTest {

    private lateinit var getPhotosAlbumsTask : GetPhotosAlbumsTask

    @Mock
    private lateinit var photoAlbumsRepository: PhotoAlbumsRepository


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        getPhotosAlbumsTask = GetPhotosAlbumsTask(
            photoAlbumsRepository,
            IoScheduler(),
            Schedulers.trampoline()
        )
    }

    @Test
    fun test_getPhotoAlbumsData_success(){

        //Arrange
        val photo1 = PhotoAlbumEntity(1,3,"title of photo 1",
            "url_photo1", "thumbnailUrl_photo1")
        val photo2 = PhotoAlbumEntity(2,3,"title of photo 2",
            "url_photo2", "thumbnailUrl_photo2")

        val listPhotos = listOf(photo1,photo2)

        //Act

        Mockito.`when`(photoAlbumsRepository.getPhotoAlbumsData())
            .thenReturn(Observable.just(listPhotos))

        val testObserver = getPhotosAlbumsTask.buildUseCase().test()


        //Assert
        Mockito.verify(photoAlbumsRepository).getPhotoAlbumsData()

        testObserver.assertSubscribed()
            .assertValue { it == listPhotos }
            .assertComplete()
    }

    @Test
    fun test_getPhotoAlbumsData_error(){

        //Arrange
        val errorMessage = "403 access denied"

        //Act
        Mockito.`when`(photoAlbumsRepository.getPhotoAlbumsData())
            .thenReturn(Observable.error(Throwable(errorMessage)))

        val testObserver = getPhotosAlbumsTask.buildUseCase().test()


        //Assert
        Mockito.verify(photoAlbumsRepository)
            .getPhotoAlbumsData()

        testObserver.assertSubscribed()
            .assertError {
                it.message?.equals(errorMessage) ?: false
            }
            .assertNotComplete()

    }
}