package com.karim.remote.api

import com.karim.remote.model.PhotoAlbumNetwork
import io.reactivex.Observable
import retrofit2.http.GET

interface PhotoAlbumsRetrofitService  {

    @GET("technical-test.json")
    fun getPhotoAlbumsData() : Observable<List<PhotoAlbumNetwork>>

}





