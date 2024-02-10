package com.musica.dashboard.player.repository

import com.musica.common.service.volley.ServiceResult
import com.musica.common.service.models.response.AlbumResponse
import com.musica.common.service.models.response.SearchAlbumsResponse
import com.musica.common.service.models.response.SearchTracksResponse
import com.musica.common.service.models.response.TrackResponse
import com.musica.dashboard.player.service.TrackService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface TrackRepository {

    suspend fun getTrack(trackId: String): ServiceResult<TrackResponse>

    suspend fun getAlbum(albumId: String): ServiceResult<AlbumResponse>

    suspend fun searchAlbum(query: String): ServiceResult<SearchAlbumsResponse>

    suspend fun searchTracks(query: String): ServiceResult<SearchTracksResponse>

}

class TrackRepositoryImpl @Inject constructor(private val trackService: TrackService) :
    TrackRepository {

    override suspend fun getTrack(trackId: String) = withContext(Dispatchers.IO) {
        trackService.getTrack(trackId)
    }

    override suspend fun getAlbum(albumId: String) = withContext(Dispatchers.IO) {
        trackService.getAlbum(albumId)
    }

    override suspend fun searchAlbum(query: String) = withContext(Dispatchers.IO) {
        trackService.searchAlbum(query)
    }

    override suspend fun searchTracks(query: String) = withContext(Dispatchers.IO) {
        trackService.searchTracks(query)
    }

}