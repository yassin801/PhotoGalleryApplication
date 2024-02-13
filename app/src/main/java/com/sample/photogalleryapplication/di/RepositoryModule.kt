package com.sample.photogalleryapplication.di

import com.sample.photogalleryapplication.network.IRedditPhotoRemote
import com.sample.photogalleryapplication.repository.PhotoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { provideRedditPhotoRepository(get()) }
}

private fun provideRedditPhotoRepository(remote: IRedditPhotoRemote): PhotoRepository = PhotoRepository(remote)