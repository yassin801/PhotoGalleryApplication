package com.sample.photogalleryapplication.network

import com.sample.photogalleryapplication.model.RedditChild
import com.sample.photogalleryapplication.model.RedditData
import com.sample.photogalleryapplication.model.RedditPost
import com.sample.photogalleryapplication.model.RedditResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RedditPhotoRemoteImplTest {

    private val testDispatcher = StandardTestDispatcher()

    private val testScope = TestScope(testDispatcher)

    private val mockService: IRedditPhotoService = mockk()

    private lateinit var remoteImpl: RedditPhotoRemoteImpl

    @Before
    fun setup() {
        remoteImpl = RedditPhotoRemoteImpl(mockService)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `getPhotos returns expected result`() = testScope.runTest {
        val keyword = "test"
        val redditData = RedditData(
            listOf(
                RedditChild(RedditPost("1500", "title", "subreddit", "image", "image_url"))
            )
        )
        coEvery { mockService.getRedditData(keyword) } returns RedditResponse(redditData)

        val result = remoteImpl.getPhotos(keyword)

        assertEquals(1, result.size)
        assertEquals("image_url", result[0].imageUrl)
        assertEquals("title", result[0].title)
        assertEquals("subreddit", result[0].subreddit)
        assertEquals("1500", result[0].upVotes)
    }

    @Test
    fun `getPhotos returns unexpected result`() = testScope.runTest {
        val keyword = "test"
        val redditData = RedditData(
            listOf(
                RedditChild(RedditPost("0", "title", "subreddit", "text", "invalid_url"))
            )
        )
        coEvery { mockService.getRedditData(keyword) } returns RedditResponse(redditData)

        val result = remoteImpl.getPhotos(keyword)

        assertEquals(0, result.size)
    }
}
