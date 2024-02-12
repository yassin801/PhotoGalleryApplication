package com.sample.photogalleryapplication.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sample.photogalleryapplication.R
import com.sample.photogalleryapplication.view.util.getStringResource

@Composable
fun ShowProgressBar() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ShowEmptyResponse() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = getStringResource(R.string.no_result),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ShowErrorResponse() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = getStringResource(R.string.network_error_result),
            color = MaterialTheme.colorScheme.error
        )
    }
}