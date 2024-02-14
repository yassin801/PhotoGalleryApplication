package com.sample.photogalleryapplication.model

import java.io.IOException

/**
 * Represents the result of UI and data operations.
 */
sealed class UiResult<out T> {

    /** Represents a successful operation with [data]. */
    data class Success<T>(val data: T) : UiResult<T>()

    /** Represents an error with [exception]. */
    data class Error(val exception: Throwable) : UiResult<Nothing>() {
        /**
         * Indicates whether the error is due to a network issue.
         * @return true if the error is a network error, false otherwise.
         */
        val isNetworkError: Boolean get() = exception is IOException
    }

    /** Represents an empty result. */
    data object Empty : UiResult<Nothing>()

    /** Represents a loading state. */
    data object Loading : UiResult<Nothing>()

    companion object {

        /**
         * Creates a [Success] result with the provided [data].
         * @param data The data to emit.
         * @return A [Success] result with the provided data.
         */
        fun <T> success(data: T) = Success(data)

        /**
         * Creates an [Error] result with the provided [exception].
         * @param exception The exception representing the error.
         * @return An [Error] result with the provided exception.
         */
        fun error(exception: Throwable) = Error(exception)

        /**
         * Creates an [Empty] result.
         * @return An [Empty] result.
         */
        fun empty() = Empty

        /**
         * Creates a [Loading] result.
         * @return A [Loading] result.
         */
        fun loading() = Loading

        /**
         * Creates a [Success] result with the provided [list] if it's not empty, otherwise creates an [Empty] result.
         * @param list The list to check.
         * @return A [Success] result with the provided list if not empty, otherwise an [Empty] result.
         */
        fun <T> successOrEmpty(list: List<T>): UiResult<List<T>> = if (list.isEmpty()) empty() else success(list)
    }
}