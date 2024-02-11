package com.sample.photogalleryapplication.repository

import com.sample.photogalleryapplication.viewmodel.PhotoGalleryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { providePhotoGalleryViewModel(get()) }
}

private fun providePhotoGalleryViewModel(repository: PhotoRepository): PhotoGalleryViewModel {
    return PhotoGalleryViewModel(repository)
}