package com.sample.photogalleryapplication.network

import com.sample.photogalleryapplication.model.RedditResponse
import retrofit2.http.GET
import retrofit2.http.Path

/** Interface used to connect to the Reddit API to fetch data. */
interface IRedditPhotoService {

    @GET("/r/{keyword}/top.json")
    suspend fun getRedditData(@Path("keyword") keyword: String): RedditResponse
}