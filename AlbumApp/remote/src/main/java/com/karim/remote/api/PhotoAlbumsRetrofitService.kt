package com.karim.remote.api

import com.karim.remote.BuildConfig
import com.karim.remote.model.PhotoAlbumNetwork
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface PhotoAlbumsService {

    fun getPhotoAlbumsData() : Observable<List<PhotoAlbumNetwork>>

    companion object {
        fun createService(): PhotoAlbumsService = PhotoAlbumsApiServiceImpl()
    }
}

interface PhotoAlbumsRetrofitService {

    @GET("technical-test.json")
    fun getPhotoAlbumsData() : Observable<List<PhotoAlbumNetwork>>

}

internal class PhotoAlbumsApiServiceImpl : PhotoAlbumsService {



    private val service : PhotoAlbumsRetrofitService = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .build()
        .create(PhotoAlbumsRetrofitService::class.java)

    override fun getPhotoAlbumsData(): Observable<List<PhotoAlbumNetwork>> {
        return service.getPhotoAlbumsData()
    }


}



