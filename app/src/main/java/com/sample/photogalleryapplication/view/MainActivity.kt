package com.sample.photogalleryapplication.view

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.sample.photogalleryapplication.view.theme.PhotoGalleryApplicationTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.sample.photogalleryapplication.di.networkModule
import com.sample.photogalleryapplication.di.viewModelModule
import com.sample.photogalleryapplication.di.repositoryModule
import com.sample.photogalleryapplication.model.Photo
import com.sample.photogalleryapplication.viewmodel.PhotoGalleryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainActivity : ComponentActivity() {

    private val viewModel: PhotoGalleryViewModel by viewModel()
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoinIfNeeded()
        installSplashScreen()
        setContent {
            val navController = rememberNavController()

            SetupNavigation(navController)
        }
    }

    /**
     * Checks if Koin is already started, and starts it if needed.
     */
    private fun startKoinIfNeeded() {
        if (!isKoinStarted) {
            startKoin {
                androidLogger(Level.ERROR)
                androidContext(this@MainActivity)
                modules(
                    viewModelModule,
                    repositoryModule,
                    networkModule
                )
            }.also {
                isKoinStarted = true
            }
        }
    }

    /**
     * Navigates to the detail screen with the specified photo.
     *
     * @param navController The NavController for navigating between destinations.
     * @param photo The photo object to pass to the detail screen.
     */
    private fun navigateToDetailPage(navController: NavHostController, photo: Photo) {
        navController.navigate("$ROUTE_PHOTO_DETAIL_PAGE/${Uri.encode(gson.toJson(photo))}")
    }

    /**
     * Sets up the navigation graph.
     *
     * @param navController The NavController for navigating between destinations.
     */
    @Composable
    private fun SetupNavigation(navController: NavHostController) {
        NavHost(navController, startDestination = ROUTE_PHOTO_GALLERY_PAGE) {
            composable(ROUTE_PHOTO_GALLERY_PAGE) {
                PhotoGalleryApplicationTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        ShowPhotoGalleryPage(viewModel) { photo ->
                            navigateToDetailPage(navController, photo)
                        }
                    }
                }
            }
            composable("$ROUTE_PHOTO_DETAIL_PAGE/{$PHOTO_JSON}") { backStackEntry ->
                PhotoGalleryApplicationTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        DetailPage(navController, backStackEntry.parsePhoto())
                    }
                }
            }
        }
    }

    /**
     * Parses the [Photo] object from the arguments of the [NavBackStackEntry].
     *
     * @return The parsed [Photo] object, or null if parsing fails.
     */
    private fun NavBackStackEntry.parsePhoto() =
        gson.fromJson(arguments?.getString(PHOTO_JSON), Photo::class.java)

    private companion object {
        /** Flag indicating whether Koin has been started. */
        var isKoinStarted = false

        /** Key for the photo gallery page route. */
        const val ROUTE_PHOTO_GALLERY_PAGE = "PhotoGalleryPage"

        /** Key for the photo detail page route. */
        const val ROUTE_PHOTO_DETAIL_PAGE = "photoDetailPage"

        /** Key for passing photo JSON data between destinations. */
        const val PHOTO_JSON = "photoJson"
    }
}