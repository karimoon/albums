package com.karim.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.karim.domain.models.PhotoAlbumEntity
import com.karim.domain.repository.PhotoAlbumsRepository
import com.karim.domain.usecases.GetPhotosAlbumsTask
import com.karim.presentation.mapper.PhotoAlbumEntityMapper
import com.karim.presentation.model.Status
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class AlbumsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val photoAlbumEntityMapper = PhotoAlbumEntityMapper()

    private lateinit var albumsViewModel : AlbumsViewModel

    private lateinit var getPhotosAlbumsTask: GetPhotosAlbumsTask

    private lateinit var listPhotos : List<PhotoAlbumEntity>

    @Mock
    private lateinit var photoAlbumsRepository: PhotoAlbumsRepository

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        val photo1 = PhotoAlbumEntity(1,3,"title of photo 1",
            "url_photo1", "thumbnailUrl_photo1")
        val photo2 = PhotoAlbumEntity(2,3,"title of photo 2",
            "url_photo2", "thumbnailUrl_photo2")

        listPhotos = listOf(photo1,photo2)


        getPhotosAlbumsTask = GetPhotosAlbumsTask(
            photoAlbumsRepository,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )

    }

    @Test
    fun getPhotoAlbumList_success(){

        //Arrange

        //Act

        Mockito.`when`(photoAlbumsRepository.getPhotoAlbumsData())
            .thenReturn(Observable.just(listPhotos))

        albumsViewModel = AlbumsViewModel(
            photoAlbumEntityMapper,
            getPhotosAlbumsTask
        )

        val photoAlbumListSource = albumsViewModel.photoAlbumListSource

        photoAlbumListSource.observeForever {

        }

        //Assert

        Assert.assertEquals(photoAlbumListSource.value?.status , Status.SUCCESS)

        Assert.assertEquals(photoAlbumListSource.value?.data,
            listPhotos.map {
                photoAlbumEntityMapper.to(it)
            })

    }

    @Test
    fun getPhotoAlbumList_error(){

        //Arrange

        val errorMessage = "404 not found"

        //Act

        Mockito.`when`(photoAlbumsRepository.getPhotoAlbumsData())
            .thenReturn(Observable.error(Throwable(errorMessage)))

        albumsViewModel = AlbumsViewModel(
            photoAlbumEntityMapper,
            getPhotosAlbumsTask
        )

        val photoAlbumListSource = albumsViewModel.photoAlbumListSource

        photoAlbumListSource.observeForever {

        }

        //Assert

        Assert.assertEquals(photoAlbumListSource.value?.status , Status.ERROR)

        Assert.assertEquals(photoAlbumListSource.value?.message,
            errorMessage)

    }
}