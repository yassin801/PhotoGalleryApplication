package com.sample.photogalleryapplication.model

import com.google.gson.annotations.SerializedName

/**
 * Data class representing the response from Reddit API.
 *
 * @property data The data of the Reddit response.
 */
data class RedditResponse(
    @SerializedName("data") val data: RedditData
)

/**
 * Data class representing the data within the Reddit response.
 *
 * @property children The list of child elements within the Reddit data.
 */
data class RedditData(
    @SerializedName("children") val children: List<RedditChild>
)

/**
 * Data class representing a child element within the Reddit data.
 *
 * @property post The post data within the Reddit child.
 */
data class RedditChild(
    @SerializedName("data") val post: RedditPost
)

/**
 * Data class representing a post within the Reddit data.
 *
 * @property upVotes The number of up-votes received by the post.
 * @property title The title of the post.
 * @property subreddit The subreddit where the post was made.
 * @property postHint The hint about the type of post (e.g., image, video).
 * @property url The URL associated with the post.
 */
data class RedditPost(
    @SerializedName("ups") val upVotes: String,
    @SerializedName("title") val title: String,
    @SerializedName("subreddit") val subreddit: String,
    @SerializedName("post_hint") val postHint: String,
    @SerializedName("url") val url: String
)

/** Constant representing the type of post that is an image. */
const val IMAGE_TYPE = "image"