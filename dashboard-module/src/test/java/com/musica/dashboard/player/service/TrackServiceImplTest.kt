package com.musica.dashboard.player.service

import com.musica.common.service.models.response.ResponseType
import com.musica.common.service.models.response.ServiceResponse
import com.musica.common.service.volley.IService
import com.musica.common.service.volley.ServiceResult
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class TrackServiceImplTest {

    private val mockService = Mockito.mock(IService::class.java)
    private val trackService = TrackServiceImpl(mockService)

    @Test
    fun checkDevice_Successful() = runTest {
        // Arrange
        val trackId = "123"
        val response = ServiceResult(
            ServiceResponse(ResponseType.SUCCESS),
            TrackResponse(trackId, "trackName", "trackArtist", "trackUrl", "coverUrl")
        )
        whenever(mockService.GET("music/track/123", TrackResponse::class.java)).thenReturn(response)

        // Act
        val result = trackService.getTrack(trackId)

        // Assert
        Mockito.verify(mockService).GET("music/track/123", TrackResponse::class.java)
        Assert.assertEquals(response, result)
    }

    @Test
    fun getAlbum_Successful() = runTest {
        // Arrange
        val albumId = "123"
        val response = ServiceResult(
            ServiceResponse(ResponseType.SUCCESS),
            AlbumResponse(
                albumId,
                "albumName",
                "albumArtist",
                "20 July 2020",
                "coverUrl",
                listOf(),
                ""
            )
        )
        whenever(mockService.GET("music/album/123", AlbumResponse::class.java)).thenReturn(response)

        // Act
        val result = trackService.getAlbum(albumId)

        // Assert
        Mockito.verify(mockService).GET("music/album/123", AlbumResponse::class.java)
        Assert.assertEquals(response, result)
    }

    @Test
    fun searchAlbum_Successful() = runTest {
        // Arrange
        val query = "Name"
        val response = ServiceResult(
            ServiceResponse(ResponseType.SUCCESS),
            SearchAlbumsResponse(listOf())
        )
        whenever(
            mockService.GET(
                "music/album/search/Name",
                SearchAlbumsResponse::class.java
            )
        ).thenReturn(response)

        // Act
        val result = trackService.searchAlbum(query)

        // Assert
        Mockito.verify(mockService).GET("music/album/search/Name", SearchAlbumsResponse::class.java)
        Assert.assertEquals(response, result)
    }

    @Test
    fun searchTrack_Successful() = runTest {
        // Arrange
        val query = "Name"
        val response = ServiceResult(
            ServiceResponse(ResponseType.SUCCESS),
            SearchTracksResponse(listOf())
        )
        whenever(
            mockService.GET(
                "music/track/search/Name",
                SearchTracksResponse::class.java
            )
        ).thenReturn(response)

        // Act
        val result = trackService.searchTracks(query)

        // Assert
        Mockito.verify(mockService).GET("music/track/search/Name", SearchTracksResponse::class.java)
        Assert.assertEquals(response, result)
    }
}