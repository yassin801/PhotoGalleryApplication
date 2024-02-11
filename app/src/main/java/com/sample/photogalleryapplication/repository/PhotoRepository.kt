package com.sample.photogalleryapplication.repository

import com.sample.photogalleryapplication.model.Photo
import com.sample.photogalleryapplication.model.UiResult
import com.sample.photogalleryapplication.network.IRedditPhotoRemote
import com.sample.photogalleryapplication.network.RedditPhotoRemoteImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/** Repository class module for handling [Photo] data operations. */
class PhotoRepository(
    // TODO: Add DI using Koin.
    private val remote: IRedditPhotoRemote = RedditPhotoRemoteImpl()
) {
    fun getPhotos(keyword: String): Flow<UiResult<List<Photo>>> = flow {
        // TODO: Currently simulate network call to Reddit API.
        // TODO: call remote.getPhotos(keyword)
        val placeholderPhotos = listOf(
            Photo("https://via.placeholder.com/150", "Photo 1"),
            Photo("https://via.placeholder.com/150", "Photo 2"),
            Photo("https://via.placeholder.com/150", "Photo 3"),
            Photo("https://via.placeholder.com/150", "Photo 4"),
            Photo("https://via.placeholder.com/150", "Photo 5")
        )
        emit(UiResult.successOrEmpty(placeholderPhotos))
    }
}