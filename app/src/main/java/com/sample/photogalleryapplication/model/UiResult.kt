package com.sample.photogalleryapplication.model

import java.io.IOException

/** Result management for UI and data. */
sealed class UiResult<out T> {
    data class Success<T>(val data: T) : UiResult<T>()

    data class Error(val exception: Throwable) : UiResult<Nothing>() {
        /** If the [exception] is of type [IOException] then a network error was occurred. */
        val isNetworkError: Boolean get() = exception is IOException
    }

    data object Empty : UiResult<Nothing>()

    data object Loading : UiResult<Nothing>()

    companion object {

        /** Return [Success] with [data] to emit. */
        fun <T> success(data: T) = Success(data)

        /** Return [Error] with [exception] to emit. */
        fun error(exception: Throwable) = Error(exception)

        /** Return [Empty]. */
        fun empty() = Empty

        /** Return [Loading]. */
        fun loading() = Loading

        /** Return [Empty] if [list] is empty, otherwise return [Success]. */
        fun <T> successOrEmpty(list: List<T>): UiResult<List<T>> {
            return if (list.isEmpty()) Empty else Success(list)
        }
    }
}