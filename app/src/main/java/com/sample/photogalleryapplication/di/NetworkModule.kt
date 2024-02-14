package com.sample.photogalleryapplication.di

import com.sample.photogalleryapplication.BuildConfig
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

/**
 * Provides a [HttpLoggingInterceptor] for logging HTTP requests and responses.
 * @return A configured [HttpLoggingInterceptor] instance.
 */
private fun provideLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

/**
 * Provides an [OkHttpClient] instance with configured interceptors.
 * @param loggingInterceptor The logging interceptor.
 * @return A configured [OkHttpClient] instance.
 */
private fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

/**
 * Provides a [Retrofit] instance for making HTTP requests.
 * @param okHttpClient The OkHttpClient instance.
 * @return A configured [Retrofit] instance.
 */
private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

/**
 * Provides an instance of [IRedditPhotoService] for accessing Reddit photo APIs.
 * @param retrofit The Retrofit instance.
 * @return An instance of [IRedditPhotoService].
 */
private fun provideRedditService(retrofit: Retrofit): IRedditPhotoService =
    retrofit.create(IRedditPhotoService::class.java)

/**
 * Provides an implementation of [IRedditPhotoRemote] using [IRedditPhotoService].
 * @param service The Reddit photo service.
 * @return An instance of [RedditPhotoRemoteImpl].
 */
private fun provideRedditImageRemoteImpl(service: IRedditPhotoService): RedditPhotoRemoteImpl =
    RedditPhotoRemoteImpl(service)