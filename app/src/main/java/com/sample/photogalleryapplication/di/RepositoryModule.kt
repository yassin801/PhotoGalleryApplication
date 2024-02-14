package com.sample.photogalleryapplication.di

import com.sample.photogalleryapplication.network.IRedditPhotoRemote
import com.sample.photogalleryapplication.repository.PhotoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { provideRedditPhotoRepository(get()) }
}

/**
 * Provides an instance of [PhotoRepository] using the provided [IRedditPhotoRemote].
 * @param remote The Reddit photo remote data source.
 * @return An instance of [PhotoRepository].
 */
private fun provideRedditPhotoRepository(remote: IRedditPhotoRemote): PhotoRepository =
    PhotoRepository(remote)