package com.musica.dashboard.player.service


import com.musica.common.service.models.response.AlbumResponse
import com.musica.common.service.models.response.ArtistPopularTracksResponse
import com.musica.common.service.models.response.PopularArtistResponse
import com.musica.common.service.models.response.SearchAlbumsResponse
import com.musica.common.service.models.response.SearchTracksResponse
import com.musica.common.service.models.response.TrackPlayedRequest
import com.musica.common.service.models.response.TrackResponse
import com.musica.common.service.volley.IService
import com.musica.common.service.volley.ServiceResult
import javax.inject.Inject

interface TrackService {

    suspend fun getTrack(trackId: String): ServiceResult<TrackResponse>

    suspend fun getAlbum(albumId: String): ServiceResult<AlbumResponse>

    suspend fun searchAlbum(query: String): ServiceResult<SearchAlbumsResponse>

    suspend fun searchTracks(query: String): ServiceResult<SearchTracksResponse>

    suspend fun trackPlayed(trackId: String): ServiceResult<Void>

    suspend fun getPopularArtistsTracks(artistName: String): ServiceResult<ArtistPopularTracksResponse>

    suspend fun getPopularArtists(): ServiceResult<PopularArtistResponse>

}

const val GET_TRACK_URL = "music/track/%s"
const val GET_ALBUM_URL = "music/album/%s"
const val SEARCH_ALBUM_URL = "music/album/search/%s"
const val SEARCH_TRACKS_URL = "music/track/search/%s"
const val TRACK_PLAYED_URL = "music/played/%s"
const val GET_POPULAR_ARTISTS_TRACKS = "music/popular-artist-tracks/%s"
const val GET_POPULAR_ARTISTS = "music/popular-artist"

class TrackServiceImpl @Inject constructor(private val service: IService): TrackService {

    override suspend fun getTrack(trackId: String): ServiceResult<TrackResponse> {
        return service.GET(GET_TRACK_URL.format(trackId), TrackResponse::class.java)
    }

    override suspend fun getAlbum(albumId: String): ServiceResult<AlbumResponse> {
        return service.GET(GET_ALBUM_URL.format(albumId), AlbumResponse::class.java)
    }

    override suspend fun searchAlbum(query: String): ServiceResult<SearchAlbumsResponse> {
        return service.GET(SEARCH_ALBUM_URL.format(query), SearchAlbumsResponse::class.java)
    }

    override suspend fun searchTracks(query: String): ServiceResult<SearchTracksResponse> {
        return service.GET(SEARCH_TRACKS_URL.format(query), SearchTracksResponse::class.java)
    }

    override suspend fun trackPlayed(trackId: String): ServiceResult<Void> {
        return service.PUT(TRACK_PLAYED_URL.format(trackId), Void::class.java)
    }

    override suspend fun getPopularArtistsTracks(artistName: String): ServiceResult<ArtistPopularTracksResponse> {
        return service.GET(GET_POPULAR_ARTISTS_TRACKS.format(artistName), ArtistPopularTracksResponse::class.java)
    }

    override suspend fun getPopularArtists(): ServiceResult<PopularArtistResponse> {
        return service.GET(GET_POPULAR_ARTISTS, PopularArtistResponse::class.java)
    }

}