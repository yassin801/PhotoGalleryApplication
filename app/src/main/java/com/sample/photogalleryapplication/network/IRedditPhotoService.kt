package com.sample.photogalleryapplication.network

import com.sample.photogalleryapplication.model.RedditResponse

/** Interface used to connect to the Reddit API to fetch data. */
interface IRedditPhotoService {
    // TODO: Add Retrofit dependency + implementation.
    suspend fun getRedditData(keyword: String): RedditResponse
}