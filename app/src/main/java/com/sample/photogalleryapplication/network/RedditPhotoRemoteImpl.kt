package com.sample.photogalleryapplication.network

import com.sample.photogalleryapplication.model.IMAGE_TYPE
import com.sample.photogalleryapplication.model.Photo
import com.sample.photogalleryapplication.model.RedditPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Implementation of [IRedditPhotoRemote] that connects to the Reddit API via [IRedditPhotoService].
 *
 * @param service The service used to fetch data from the Reddit API.
 */
class RedditPhotoRemoteImpl(private val service: IRedditPhotoService) : IRedditPhotoRemote {

    /**
     * Fetches photos from the Reddit API based on the provided keyword.
     *
     * @param keyword The keyword to search for photos.
     * @return A list of [Photo] objects representing the fetched photos.
     */
    override suspend fun getPhotos(keyword: String): List<Photo> = withContext(Dispatchers.Default) {
        service.getRedditData(keyword).data.children.mapNotNull { it.post.toImage() }
    }

    /**
     * Transforms a [RedditPost] object to a [Photo] object if the post is an image, otherwise returns null.
     *
     * @return The transformed [Photo] object, or null if the post is not an image.
     */
    private fun RedditPost.toImage(): Photo? =
        if (postHint == IMAGE_TYPE)
            Photo(url, title, subreddit, upVotes)
        else
            null
}