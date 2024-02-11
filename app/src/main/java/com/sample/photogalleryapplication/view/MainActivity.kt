package com.sample.photogalleryapplication.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.sample.photogalleryapplication.view.theme.PhotoGalleryApplicationTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
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
                    ShowPhotoGalleryPage(viewModel) {}
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