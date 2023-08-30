package com.musica.dashboard.player.repository

import com.musica.common.service.volley.ServiceResult
import com.musica.dashboard.player.service.AlbumResponse
import com.musica.dashboard.player.service.TrackService
import com.musica.dashboard.player.service.TrackResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface TrackRepository {

    suspend fun getTrack(trackId: String): ServiceResult<TrackResponse>

    suspend fun getAlbum(albumId: String): ServiceResult<AlbumResponse>

}

class TrackRepositoryImpl @Inject constructor(private val trackService: TrackService) :
    TrackRepository {

    override suspend fun getTrack(trackId: String) = withContext(Dispatchers.IO) {
        trackService.getTrack(trackId)
    }

    override suspend fun getAlbum(albumId: String) = withContext(Dispatchers.IO){
        trackService.getAlbum(albumId)
    }

}