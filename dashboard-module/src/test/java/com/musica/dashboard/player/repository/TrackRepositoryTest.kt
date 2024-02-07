package com.musica.dashboard.player.repository

import com.musica.common.service.models.response.ResponseType
import com.musica.common.service.models.response.ServiceResponse
import com.musica.common.service.volley.ServiceResult
import com.musica.dashboard.player.service.AlbumResponse
import com.musica.dashboard.player.service.SearchAlbumsResponse
import com.musica.dashboard.player.service.SearchTracksResponse
import com.musica.dashboard.player.service.TrackResponse
import com.musica.dashboard.player.service.TrackService
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class TrackRepositoryTest {

    private val service = Mockito.mock(TrackService::class.java)
    private val deviceRepo = TrackRepositoryImpl(service)

    @Test
    fun getTrack_Successful() = runTest {
        // Arrange
        val response = ServiceResult<TrackResponse>(ServiceResponse(ResponseType.SUCCESS))
        val trackId = "123"
        whenever(service.getTrack(trackId)).thenReturn(response)

        // Act
        val result = deviceRepo.getTrack(trackId)

        // Assert
        Mockito.verify(service).getTrack(trackId)
        Assert.assertEquals(response, result)
    }

    @Test
    fun getAlbum_Successful() = runTest {
        // Arrange
        val response = ServiceResult<AlbumResponse>(ServiceResponse(ResponseType.SUCCESS))
        val trackId = "123"
        whenever(service.getAlbum(trackId)).thenReturn(response)

        // Act
        val result = deviceRepo.getAlbum(trackId)

        // Assert
        Mockito.verify(service).getAlbum(trackId)
        Assert.assertEquals(response, result)
    }

    @Test
    fun searchTracks_Successful() = runTest {
        // Arrange
        val response = ServiceResult<SearchTracksResponse>(ServiceResponse(ResponseType.SUCCESS))
        val query = "Name"
        whenever(service.searchTracks(query)).thenReturn(response)

        // Act
        val result = deviceRepo.searchTracks(query)

        // Assert
        Mockito.verify(service).searchTracks(query)
        Assert.assertEquals(response, result)
    }

    @Test
    fun searchAlbum_Successful() = runTest {
        // Arrange
        val response = ServiceResult<SearchAlbumsResponse>(ServiceResponse(ResponseType.SUCCESS))
        val query = "Name"
        whenever(service.searchAlbum(query)).thenReturn(response)

        // Act
        val result = deviceRepo.searchAlbum(query)

        // Assert
        Mockito.verify(service).searchAlbum(query)
        Assert.assertEquals(response, result)
    }
}