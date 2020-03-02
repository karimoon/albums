package com.karim.remote.mapper

import com.karim.data.models.PhotoAlbumData
import com.karim.remote.model.PhotoAlbumNetwork

class PhotoAlbumDataNetworkMapper : Mapper<PhotoAlbumData, PhotoAlbumNetwork> {
    override fun from(e: PhotoAlbumNetwork): PhotoAlbumData {
        return PhotoAlbumData(
            id = e.id,
            albumId = e.albumId,
            title = e.title,
            url = e.url,
            thumbnailUrl = e.thumbnailUrl
        )
    }

    override fun to(t: PhotoAlbumData): PhotoAlbumNetwork {
        return PhotoAlbumNetwork(
            id = t.id,
            albumId = t.albumId,
            title = t.title,
            url = t.url,
            thumbnailUrl = t.thumbnailUrl
        )
    }
}