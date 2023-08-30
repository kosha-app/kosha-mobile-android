package com.musica.dashboard.player.service


import com.musica.common.service.volley.IService
import com.musica.common.service.volley.ServiceException
import com.musica.common.service.volley.ServiceResult
import javax.inject.Inject

interface TrackService {

    suspend fun getTrack(trackId: String): ServiceResult<TrackResponse>

    suspend fun getAlbum(albumId: String): ServiceResult<AlbumResponse>

}

const val GET_TRACK_URL = "music/track/%s"
const val GET_ALBUM_URL = "music/album/%s"

class TrackServiceImpl @Inject constructor(private val service: IService): TrackService {

    override suspend fun getTrack(trackId: String): ServiceResult<TrackResponse> {
         return   service.GET(GET_TRACK_URL.format(trackId), TrackResponse::class.java)
    }

    override suspend fun getAlbum(albumId: String): ServiceResult<AlbumResponse> {
        return service.GET(GET_ALBUM_URL.format(albumId), AlbumResponse::class.java)
    }
}