package com.musica.dashboard.player.repository

import com.musica.common.service.volley.ServiceResult
import com.musica.common.service.models.response.AlbumResponse
import com.musica.common.service.models.response.ArtistPopularTracksResponse
import com.musica.common.service.models.response.PopularArtistResponse
import com.musica.common.service.models.response.SearchAlbumsResponse
import com.musica.common.service.models.response.SearchTracksResponse
import com.musica.common.service.models.response.TrackPlayedRequest
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

    suspend fun trackPlayed(trackId: String): ServiceResult<Void>

    suspend fun getPopularArtistsTracks(artistName: String): ServiceResult<ArtistPopularTracksResponse>

    suspend fun getPopularArtists(): ServiceResult<PopularArtistResponse>

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

    override suspend fun trackPlayed(trackId: String) = withContext(Dispatchers.IO) {
        trackService.trackPlayed(trackId)
    }

    override suspend fun getPopularArtistsTracks(artistName: String) = withContext(Dispatchers.IO) {
       trackService.getPopularArtistsTracks(artistName)
    }

    override suspend fun getPopularArtists() = withContext(Dispatchers.IO) {
        trackService.getPopularArtists()
    }

}