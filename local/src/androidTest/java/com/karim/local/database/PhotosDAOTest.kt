package com.karim.local.database

import com.karim.local.model.PhotoAlbumLocal
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class PhotosDAOTest : PhotoAlbumDbTest() {

    private lateinit var listPhotosWithDifferentIds : List<PhotoAlbumLocal>
    private lateinit var listPhotosWithSameIds : List<PhotoAlbumLocal>


    @Before
    fun setup(){

        val photo1 = PhotoAlbumLocal(1,3,"title of photo 1",
            "url_photo1", "thumbnailUrl_photo1")
        val photo2 = PhotoAlbumLocal(2,3,"title of photo 2",
            "url_photo2", "thumbnailUrl_photo2")

        val photo3 = PhotoAlbumLocal(1,4,"title of photo 3",
            "url_photo3", "thumbnailUrl_photo3")

        listPhotosWithDifferentIds = listOf(photo1,photo2)

        listPhotosWithSameIds = listOf(photo1,photo3)
    }

    @Test
    @Throws(Exception::class)
    fun addPhotos_with_different_ids_success(){

        val testObservable = getPhotosDao().addPhotos(listPhotosWithDifferentIds).test()

        testObservable.assertSubscribed()
            .assertValues(listOf(1,2))
            .assertComplete()

    }

    @Test
    @Throws(Exception::class)
    fun addPhotos_with_same_ids_error(){

        val testObservable = getPhotosDao().addPhotos(listPhotosWithSameIds).test()

        testObservable.assertSubscribed()
           .assertValues(listOf(1,-1))
            .assertComplete()

    }

}