package com.sample.photogalleryapplication.repository

import com.sample.photogalleryapplication.model.Photo
import com.sample.photogalleryapplication.model.UiResult
import com.sample.photogalleryapplication.network.IRedditPhotoRemote
import com.sample.photogalleryapplication.network.RedditPhotoRemoteImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * Repository class responsible for handling operations related to [Photo] data.
 *
 * @param remote The remote data source for fetching photos.
 */
class PhotoRepository(private val remote: IRedditPhotoRemote) {

    /**
     * Calls the remote API to fetch a list of photos based on the provided keyword.
     *
     * @param keyword The keyword to search for photos.
     * @return A Flow emitting [UiResult] objects representing the status of the operation.
     */
    fun getPhotos(keyword: String): Flow<UiResult<List<Photo>>> = flow {
        emit(UiResult.loading())
        emit(UiResult.successOrEmpty(remote.getPhotos(keyword)))
    }.catch { error ->
        emit(UiResult.error(error))
    }
}