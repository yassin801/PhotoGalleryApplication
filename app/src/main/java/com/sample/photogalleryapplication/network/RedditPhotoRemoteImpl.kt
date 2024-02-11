package com.sample.photogalleryapplication.network

import com.sample.photogalleryapplication.model.Photo

class RedditPhotoRemoteImpl(
    // TODO: Add DI using Koin.
    val service: IRedditPhotoService? = null
) : IRedditPhotoRemote {
    override suspend fun getPhotos(keyword: String): List<Photo> {
        TODO("Not yet implemented")
        // TODO: use service.getRedditData(keyword)
    }
}