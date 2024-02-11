package com.sample.photogalleryapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sample.photogalleryapplication.model.Photo
import com.sample.photogalleryapplication.model.UiResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/** The [ViewModel] for fetching a list of top [Photo] from a subreddit. */
class PhotoGalleryViewModel : ViewModel() {

    /** The current text to search. */
    private val keyword = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val photos: LiveData<UiResult<List<Photo>>> = keyword
        // Discard texts typed in a very short time to avoid many network calls.
        .debounce(DEBOUNCE_MILLIS)
        // Filter out empty text to avoid unnecessary network call.
        .filter { text ->
            text.isNotEmpty()
        }
        // When a new text is set then trigger a network call.
        .flatMapLatest { text ->
            fetchDataFromReddit(text)
        }
        // Create a LiveData from Flow.
        .asLiveData()

    fun searchPhotos(keywordToSearch: String) {
        keyword.value = keywordToSearch
    }

    // TODO: Create a repository class and move this function there, for now return a hardcoded list.
    private fun fetchDataFromReddit(keyword: String): Flow<UiResult<List<Photo>>> = flow {
        // TODO: Simulate network call to Reddit API.
        val placeholderPhotos = listOf(
            Photo("https://via.placeholder.com/150", "Photo 1"),
            Photo("https://via.placeholder.com/150", "Photo 2"),
            Photo("https://via.placeholder.com/150", "Photo 3"),
            Photo("https://via.placeholder.com/150", "Photo 4"),
            Photo("https://via.placeholder.com/150", "Photo 5")
        )
        emit(UiResult.successOrEmpty(placeholderPhotos))
    }

    private companion object {
        const val DEBOUNCE_MILLIS = 700L
    }
}
