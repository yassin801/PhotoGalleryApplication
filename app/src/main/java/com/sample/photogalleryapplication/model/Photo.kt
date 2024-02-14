package com.sample.photogalleryapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data class representing a Reddit photo.
 *
 * @property imageUrl The URL of the photo.
 * @property title The title of the photo.
 * @property subreddit The subreddit where the photo was posted.
 * @property upVotes The number of up-votes received by the photo.
 */
@Parcelize
data class Photo(
    val imageUrl: String,
    val title: String,
    val subreddit: String,
    val upVotes: String,
)  : Parcelable