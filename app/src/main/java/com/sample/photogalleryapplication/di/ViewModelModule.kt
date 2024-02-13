package com.sample.photogalleryapplication.di

import com.sample.photogalleryapplication.repository.PhotoRepository
import com.sample.photogalleryapplication.viewmodel.PhotoGalleryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { providePhotoGalleryViewModel(get()) }
}

private fun providePhotoGalleryViewModel(repository: PhotoRepository) = PhotoGalleryViewModel(repository)