package com.sample.photogalleryapplication.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.sample.photogalleryapplication.model.Photo
import com.sample.photogalleryapplication.model.UiResult
import com.sample.photogalleryapplication.viewmodel.PhotoGalleryViewModel

@Composable
fun ShowPhotoGalleryPage(viewModel: PhotoGalleryViewModel, onItemClick: (Photo) -> Unit) {

    var uiResult by remember { mutableStateOf<UiResult<List<Photo>>?>(null) }

    var searchText by rememberSaveable { mutableStateOf("") }

    var photos by rememberSaveable { mutableStateOf<List<Photo>>(emptyList()) }

    /** LiveData observer configuration. */
    viewModel.photos.observe(LocalLifecycleOwner.current) { uiResult = it }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBox(searchText) { newText ->
            searchText = newText
            viewModel.searchPhotos(newText)
        }

        /** Show the adequate element based on the UI Result. */
        when (val result = uiResult) {
            is UiResult.Loading -> ShowProgressBar()

            is UiResult.Success -> {
                photos = result.data
                if (photos.isNotEmpty())
                    ShowPhotoGrid(photos, onItemClick)
                else
                    ShowEmptyResponse()
            }

            is UiResult.Error -> {
                if (result.isNetworkError)
                    ShowErrorResponse()
                else
                    ShowEmptyResponse()
            }

            else -> ShowEmptyResponse()
        }
    }
}

@Composable
fun SearchBox(
    searchText: String,
    onSearchTextChange: (String) -> Unit
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        label = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(18.dp)
                )
                Text("Search", color = MaterialTheme.colorScheme.onSurface)
            }
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun ShowPhotoGrid(photosToShow: List<Photo>, onItemClick: (Photo) -> Unit) {
    LazyColumn(modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)) {
        items(photosToShow.chunked(3)) { rowOfPhotos ->
            RowOfPhotos(rowOfPhotos = rowOfPhotos, onItemClick = onItemClick)
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