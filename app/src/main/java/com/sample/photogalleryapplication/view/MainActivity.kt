package com.sample.photogalleryapplication.view

import android.os.Bundle
import androidx.compose.runtime.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sample.photogalleryapplication.view.theme.PhotoGalleryApplicationTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.sample.photogalleryapplication.model.Photo
import com.sample.photogalleryapplication.model.UiResult
import com.sample.photogalleryapplication.di.networkModule
import com.sample.photogalleryapplication.di.viewModelModule
import com.sample.photogalleryapplication.di.repositoryModule
import com.sample.photogalleryapplication.viewmodel.PhotoGalleryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainActivity : ComponentActivity() {

    private val viewModel: PhotoGalleryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoinIfNeeded()
        setContent {
            PhotoGalleryApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PhotoGalleryApp(viewModel)
                }
            }
        }
    }

    /** Koin configuration. */
    private fun startKoinIfNeeded() {
        if (!koinStarted)
            startKoin {
                androidLogger(Level.ERROR)
                androidContext(this@MainActivity)
                modules(
                    viewModelModule,
                    repositoryModule,
                    networkModule
                )
            }.also {
                koinStarted = true
            }
    }

    companion object {
        private var koinStarted = false
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PhotoGalleryAppPreview() {
//    PhotoGalleryApplicationTheme {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            PhotoGalleryApp()
//        }
//    }
//}

@Composable
fun PhotoGalleryApp(viewModel: PhotoGalleryViewModel) {

    var uiResult by remember { mutableStateOf<UiResult<List<Photo>>?>(null) }

    var searchText by rememberSaveable { mutableStateOf("") }

    var photos by rememberSaveable { mutableStateOf<List<Photo>>(emptyList()) }

    /** LiveData observer configuration. */
    viewModel.photos.observe(LocalLifecycleOwner.current) { uiResult = it }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Search box
        OutlinedTextField(
            value = searchText,
            onValueChange = { newText ->
                searchText = newText
                viewModel.searchPhotos(newText)
            },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        /** Show the adequate element based on the UI Result. */
        when (val result = uiResult) {
            is UiResult.Loading -> {
                ShowProgressBar()
            }

            is UiResult.Success -> {
                photos = result.data
                if (photos.isNotEmpty())
                    ShowPhotoGrid(photos) {
                        // TODO: Add logic to navigate to details page here.
                    }
                else
                    ShowEmptyResponse()
            }

            is UiResult.Error -> {
                if (result.isNetworkError) {
                    ShowErrorResponse()
                } else {
                    ShowEmptyResponse()
                }
            }

            else -> {
                ShowEmptyResponse()
            }
        }
    }
}