package com.sample.photogalleryapplication.view.util

import android.content.Context
import org.koin.mp.KoinPlatform

/**
 * Retrieves a string resource from the Android context associated with the provided prompt ID.
 *
 * @param promptId The resource ID of the string to retrieve.
 * @return The string value associated with the provided resource ID.
 */
fun getStringResource(promptId: Int): String = try {
    KoinPlatform.getKoin().get<Context>().getString(promptId)
} catch (_: Exception) {
    // Return an empty string in case of failure.
    ""
}