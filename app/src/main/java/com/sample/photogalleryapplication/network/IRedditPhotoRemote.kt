package com.sample.photogalleryapplication.network

import com.sample.photogalleryapplication.model.Photo

/** Remote entry point to fetch photos from Reddit. */
interface IRedditPhotoRemote {
    suspend fun getPhotos(keyword: String): List<Photo>
}