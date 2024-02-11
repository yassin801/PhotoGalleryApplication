package com.sample.photogalleryapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/** Data class that represents a Reddit photo. */
@Parcelize
data class Photo(
    val imageUrl: String,
    val title: String,
    val subreddit: String,
    val upVotes: String,
)  : Parcelable