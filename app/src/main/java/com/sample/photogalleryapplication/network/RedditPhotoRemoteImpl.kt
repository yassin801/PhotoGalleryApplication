package com.sample.photogalleryapplication.network

import com.sample.photogalleryapplication.model.IMAGE_TYPE
import com.sample.photogalleryapplication.model.Photo
import com.sample.photogalleryapplication.model.RedditPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RedditPhotoRemoteImpl(private val service: IRedditPhotoService) : IRedditPhotoRemote {

    override suspend fun getPhotos(keyword: String) = withContext(Dispatchers.Default) {
        service.getRedditData(keyword).data.children.mapNotNull { it.post.toImage() }
    }

    /** Transform [RedditPost] to [Photo] if compatible, otherwise return [null]. */
    private fun RedditPost.toImage(): Photo? =
        if (postHint == IMAGE_TYPE)
            Photo(url, title, subreddit, upVotes)
        else
            null
}