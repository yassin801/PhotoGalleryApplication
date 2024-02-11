package com.sample.photogalleryapplication.di

import com.sample.photogalleryapplication.network.IRedditPhotoRemote
import com.sample.photogalleryapplication.network.IRedditPhotoService
import com.sample.photogalleryapplication.network.RedditPhotoRemoteImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { provideRedditService(get()) }
    single<IRedditPhotoRemote> { provideRedditImageRemoteImpl(get()) }
}

private const val BASE_URL = "https://www.reddit.com/"

private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        // TODO: Fix BuildConfig import
//        level = if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor.Level.BODY
//        } else {
//            HttpLoggingInterceptor.Level.NONE
//        }
    }
}

private fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private fun provideRedditService(retrofit: Retrofit): IRedditPhotoService =
    retrofit.create(IRedditPhotoService::class.java)

private fun provideRedditImageRemoteImpl(
    service: IRedditPhotoService
): RedditPhotoRemoteImpl = RedditPhotoRemoteImpl(service)