package com.sample.photogalleryapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sample.photogalleryapplication.model.Photo
import com.sample.photogalleryapplication.model.UiResult
import com.sample.photogalleryapplication.repository.PhotoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest

/**
 * ViewModel responsible for fetching a list of [Photo] from a subreddit.
 *
 * @param repository The repository for fetching photo data.
 */
class PhotoGalleryViewModel(private val repository: PhotoRepository) : ViewModel() {

    /** The current keyword used for searching photos. */
    private val keyword = MutableStateFlow("")

    /** LiveData representing the result of fetching photos. */
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val photos: LiveData<UiResult<List<Photo>>> = keyword
        // Discard texts typed in a very short time to avoid excessive network calls.
        .debounce(DEBOUNCE_MILLIS)
        // Filter out empty text to avoid unnecessary network calls.
        .filter { text ->
            text.isNotEmpty()
        }
        // Trigger a network call when a new keyword is set.
        .flatMapLatest { text ->
            repository.getPhotos(text)
        }
        // Convert Flow to LiveData.
        .asLiveData()

    /**
     * Sets the keyword to search for photos.
     * @param keywordToSearch The keyword to search for photos.
     */
    fun searchPhotos(keywordToSearch: String) {
        keyword.value = keywordToSearch
    }

    private companion object {
        /**
         * The debounce duration in milliseconds.
         * Used to avoid excessive network calls when typing text.
         */
        const val DEBOUNCE_MILLIS = 700L
    }
}
