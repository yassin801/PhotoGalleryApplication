package com.sample.photogalleryapplication.repository

import com.sample.photogalleryapplication.model.Photo
import com.sample.photogalleryapplication.model.UiResult
import com.sample.photogalleryapplication.network.IRedditPhotoRemote
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PhotoRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()

    private val testScope = TestScope(testDispatcher)

    private val mockRemote: IRedditPhotoRemote = mockk()

    private lateinit var repository: PhotoRepository

    @Before
    fun setup() {
        repository = PhotoRepository(mockRemote)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `getPhotos returns expected result`() = testScope.runTest {
        val keyword = "test"
        val expectedPhotos = listOf(
            Photo("url1", "title1", "subreddit1", "10"),
            Photo("url2", "title2", "subreddit2", "20")
        )
        coEvery { mockRemote.getPhotos(keyword) } returns expectedPhotos

        val result = repository.getPhotos(keyword)

        result.collect { uiResult ->
            when (uiResult) {
                is UiResult.Loading -> assertEquals(UiResult.Loading, uiResult)
                is UiResult.Success -> assertEquals(expectedPhotos, uiResult.data)
                is UiResult.Error -> fail("Expected Success or Loading, but got Error")
                is UiResult.Empty -> fail("Expected Success or Loading, but got Empty")
            }
        }
    }

    @Test
    fun `getPhotos handles error from remote`() = testScope.runTest {
        val keyword = "test"
        val expectedError = RuntimeException("Remote error")
        coEvery { mockRemote.getPhotos(keyword) } throws expectedError

        val result = repository.getPhotos(keyword)

        result.collect { uiResult ->
            when (uiResult) {
                is UiResult.Loading -> assertEquals(UiResult.Loading, uiResult)
                is UiResult.Success -> fail("Expected Error or Loading, but got Success")
                is UiResult.Error -> assertEquals(UiResult.Error(expectedError), uiResult)
                is UiResult.Empty -> fail("Expected Error or Loading, but got Empty")
            }
        }
    }

    @Test
    fun `getPhotos returns empty result`() = testScope.runTest {
        val keyword = "test"
        coEvery { mockRemote.getPhotos(keyword) } returns emptyList()

        val result = repository.getPhotos(keyword)

        result.collect { uiResult ->
            when (uiResult) {
                is UiResult.Loading -> assertEquals(UiResult.Loading, uiResult)
                is UiResult.Success -> fail("Expected Empty or Loading, but got Success")
                is UiResult.Error -> fail("Expected Empty or Loading, but got Empty")
                is UiResult.Empty -> assertEquals(UiResult.Empty, uiResult)
            }
        }
    }
}
