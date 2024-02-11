package com.sample.photogalleryapplication.repository

import com.sample.photogalleryapplication.model.RedditResponse
import com.sample.photogalleryapplication.network.IRedditPhotoRemote
import com.sample.photogalleryapplication.network.IRedditPhotoService
import com.sample.photogalleryapplication.network.RedditPhotoRemoteImpl
import org.koin.dsl.module

val networkModule = module {
    single { provideRedditService() }
    single<IRedditPhotoRemote> { provideRedditImageRemoteImpl(get()) }
}

private fun provideRedditService(): IRedditPhotoService {
    // TODO: Add retrofit implementation, for now return a dummy object
    class RedditPhotoServiceDummyImpl(): IRedditPhotoService {
        override suspend fun getRedditData(keyword: String) = RedditResponse()

    }
    return RedditPhotoServiceDummyImpl()
}

private fun provideRedditImageRemoteImpl(
    service: IRedditPhotoService
): RedditPhotoRemoteImpl {
    return RedditPhotoRemoteImpl(service)
}