package com.karim.presentation.mapper

import com.karim.domain.models.PhotoAlbumEntity
import com.karim.presentation.model.PhotoAlbum

class PhotoAlbumEntityMapper : Mapper<PhotoAlbumEntity, PhotoAlbum> {

    override fun from(e: PhotoAlbum): PhotoAlbumEntity {
        return PhotoAlbumEntity(
            id = e.id,
            albumId = e.albumId,
            title = e.title,
            url = e.url,
            thumbnailUrl = e.thumbnailUrl
        )
    }

    override fun to(t: PhotoAlbumEntity): PhotoAlbum {
        return PhotoAlbum(
            id = t.id,
            albumId = t.albumId,
            title = t.title,
            url = t.url,
            thumbnailUrl = t.thumbnailUrl
        )
    }
}