package com.karim.local.mapper

import com.karim.data.models.PhotoAlbumData
import com.karim.local.model.PhotoAlbumLocal

class PhotoAlbumDataLocalMapper : Mapper<PhotoAlbumData, PhotoAlbumLocal> {
    override fun from(e: PhotoAlbumLocal): PhotoAlbumData {
        return PhotoAlbumData(
            id = e.id,
            albumId = e.albumId,
            title = e.title,
            url = e.url,
            thumbnailUrl = e.thumbnailUrl
        )
    }

    override fun to(t: PhotoAlbumData): PhotoAlbumLocal {
        return PhotoAlbumLocal(
            id = t.id,
            albumId = t.albumId,
            title = t.title,
            url = t.url,
            thumbnailUrl = t.thumbnailUrl
        )
    }

}