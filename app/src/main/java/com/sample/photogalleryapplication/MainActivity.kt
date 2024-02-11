package com.sample.photogalleryapplication

import android.os.Bundle
import androidx.compose.runtime.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sample.photogalleryapplication.ui.theme.PhotoGalleryApplicationTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.sample.photogalleryapplication.model.Photo
import com.sample.photogalleryapplication.model.UiResult
import com.sample.photogalleryapplication.viewmodel.PhotoGalleryViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoGalleryApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PhotoGalleryApp()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhotoGalleryAppPreview() {
    PhotoGalleryApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            PhotoGalleryApp()
        }
    }
}

@Composable
fun PhotoGalleryApp() {

    var searchText by remember { mutableStateOf("") }

    val viewModel: PhotoGalleryViewModel = viewModel()

    var uiResult by remember { mutableStateOf<UiResult<List<Photo>>?>(null) }

    var photos by remember { mutableStateOf<List<Photo>>(emptyList()) }

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
                // TODO: Show loading
            }

            is UiResult.Success -> {
                // Show photos
                photos = result.data
                ShowPhotoGrid(photos)
            }

            is UiResult.Empty -> {
                // TODO: Show a message (or image) indicates that there are no photos to show
            }

            is UiResult.Error -> {
                if (result.isNetworkError) {
                    // TODO: Show a message (or image) indicates that there is no internet
                } else {
                    // TODO: Show a message (or image) indicates that there are no photos to show
                }
            }

            else -> {
                // TODO: Show a message (or image) indicates that there are no photos to show
            }
        }
    }
}

@Composable
fun ShowPhotoGrid(photosToShow: List<Photo>, gridSize: Int = 3) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(photosToShow.chunked(gridSize)) { rowOfPhotos ->
            RowOfPhotos(rowOfPhotos = rowOfPhotos, onItemClick = { photo ->
                // TODO: Navigate to detail page using the [photo] parameter.
            })
        }
    }
}

@Composable
fun RowOfPhotos(rowOfPhotos: List<Photo>, onItemClick: (Photo) -> Unit) {
    Row {
        rowOfPhotos.forEach { photo ->
            PhotoItem(photo = photo, onItemClick = onItemClick)
        }
    }
}

@Composable
fun PhotoItem(photo: Photo, onItemClick: (Photo) -> Unit) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onItemClick(photo) }
    ) {
        Image(
            painter = rememberAsyncImagePainter(photo.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(shape = RoundedCornerShape(4.dp))
        )
        Text(
            text = photo.title,
            modifier = Modifier.padding(top = 4.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}