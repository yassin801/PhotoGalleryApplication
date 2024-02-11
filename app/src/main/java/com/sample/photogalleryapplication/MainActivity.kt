package com.sample.photogalleryapplication

import android.os.Bundle
import androidx.compose.runtime.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sample.photogalleryapplication.ui.theme.PhotoGalleryApplicationTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.koin.androidx.viewmodel.ext.android.viewModel
import coil.compose.rememberAsyncImagePainter
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
        setupKoin()
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
    private fun setupKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MainActivity)
            modules(
                viewModelModule,
                repositoryModule,
                networkModule
            )
        }
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

    var searchText by remember { mutableStateOf("") }

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
                ShowProgressBar()
            }

            is UiResult.Success -> {
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
fun ShowPhotoGrid(photosToShow: List<Photo>) {
    LazyColumn(modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)) {
        items(photosToShow.chunked(3)) { rowOfPhotos ->
            RowOfPhotos(rowOfPhotos = rowOfPhotos, onItemClick = { photo ->
                // TODO: Navigate to detail page using the [photo] parameter.
            })
        }
    }
}

@Composable
fun RowOfPhotos(rowOfPhotos: List<Photo>, onItemClick: (Photo) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        rowOfPhotos.forEach { photo ->
            PhotoItem(photo = photo, onItemClick = onItemClick)
        }
    }
}

@Composable
fun PhotoItem(photo: Photo, onItemClick: (Photo) -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onItemClick(photo) },
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = Modifier.aspectRatio(1.5f)) {
            Image(
                painter = rememberAsyncImagePainter(photo.imageUrl),
                contentDescription = "Photo: ${photo.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    // Semi-transparent background to make the text visible
                    .background(color = Color.Black.copy(alpha = 0.5f))
                    .padding(8.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = photo.title,
                    style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun ShowProgressBar() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}