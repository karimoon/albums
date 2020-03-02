package com.karim.data.mapper

import com.karim.data.models.PhotoAlbumData
import com.karim.domain.models.PhotoAlbumEntity

class PhotoAlbumDomainDataMapper constructor() : Mapper<PhotoAlbumEntity, PhotoAlbumData> {

    override fun from(e: PhotoAlbumData): PhotoAlbumEntity {
        return PhotoAlbumEntity(
            id = e.id,
            albumId = e.albumId,
            title = e.title,
            url = e.url,
            thumbnailUrl = e.thumbnailUrl
        )
    }

    override fun to(t: PhotoAlbumEntity): PhotoAlbumData {
        return PhotoAlbumData(
            id = t.id,
            albumId = t.albumId,
            title = t.title,
            url = t.url,
            thumbnailUrl = t.thumbnailUrl
        )
    }
}