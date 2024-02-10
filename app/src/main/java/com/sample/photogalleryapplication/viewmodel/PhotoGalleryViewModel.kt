package com.sample.photogalleryapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.photogalleryapplication.model.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/** The [ViewModel] for fetching a list of top [Photo] from a subreddit. */
class PhotoGalleryViewModel : ViewModel() {

    private val _photos = mutableListOf<Photo>()
    val photos: List<Photo>
        get() = _photos

    fun searchPhotos(keyword: String) {
        // TODO: Simulate fetching data from Reddit API
        viewModelScope.launch {
            val result = fetchDataFromReddit(keyword)
            _photos.clear()
            _photos.addAll(result)
        }
    }

    private suspend fun fetchDataFromReddit(keyword: String): List<Photo> {
        // TODO: Simulate network call to Reddit API.
        return withContext(Dispatchers.IO) {
            // TODO: Implement logic to fetch data from Reddit API here,
            //  for now use this placeholder implementation by returning a hardcoded list of photos.
            val placeholderPhotos = listOf(
                Photo("https://via.placeholder.com/150", "Photo 1"),
                Photo("https://via.placeholder.com/150", "Photo 2"),
                Photo("https://via.placeholder.com/150", "Photo 3"),
                Photo("https://via.placeholder.com/150", "Photo 4"),
                Photo("https://via.placeholder.com/150", "Photo 5"),
            )
            placeholderPhotos
        }
    }
}
