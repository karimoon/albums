package com.karim.presentation.sharedUiRepos

import com.karim.presentation.model.PhotoAlbum


/*
this is singleton repo which is injected by koin , allow us to share selected photo
 between AlbumsFragment and DetailFragment
*/
class SharedUIRepo {

    var selectedPhoto : PhotoAlbum? = null

}