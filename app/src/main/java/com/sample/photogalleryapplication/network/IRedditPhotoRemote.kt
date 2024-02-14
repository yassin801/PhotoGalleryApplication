package com.sample.photogalleryapplication.network

import com.sample.photogalleryapplication.model.Photo

/**
 * Interface defining the remote entry point to fetch photos from Reddit.
 */
interface IRedditPhotoRemote {
    /**
     * Fetches photos from Reddit based on the provided keyword.
     *
     * @param keyword The keyword to search for photos.
     * @return A list of [Photo] objects representing the fetched photos.
     */
    suspend fun getPhotos(keyword: String): List<Photo>
}