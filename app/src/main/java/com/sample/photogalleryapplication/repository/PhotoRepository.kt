package com.sample.photogalleryapplication.repository

import com.sample.photogalleryapplication.model.Photo
import com.sample.photogalleryapplication.model.UiResult
import com.sample.photogalleryapplication.network.IRedditPhotoRemote
import com.sample.photogalleryapplication.network.RedditPhotoRemoteImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/** Repository class module for handling [Photo] data operations. */
class PhotoRepository(private val remote: IRedditPhotoRemote) {

    /** Call remote QPI and fetch a list of [Photo]. */
    fun getPhotos(keyword: String): Flow<UiResult<List<Photo>>> = flow {
        emit(UiResult.loading())
        val photos = remote.getPhotos(keyword)
        emit(UiResult.successOrEmpty(photos))
    }.catch { error ->
        emit(UiResult.error(error))
    }
}