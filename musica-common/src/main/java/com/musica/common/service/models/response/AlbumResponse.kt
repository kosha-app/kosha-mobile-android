package com.musica.common.service.models.response

import com.musica.common.service.models.response.TrackResponse.Companion.toTrack

class AlbumResponse(
    val id: String?,
    val albumName: String?,
    val albumArtist: String?,
    val releaseDate: String?,
    val coverUrl: String?,
    val tracks: List<TrackResponse>,
    val message: String?
) {
    companion object {
        fun AlbumResponse.toAlbum(): Album {
            return Album(
                albumId = id,
                albumName = albumName,
                artistName = albumArtist,
                coverUrl = coverUrl,
                releaseDate = releaseDate,
                tracks = tracks.map { track -> track.toTrack() }
            )
        }
    }
}

class SearchAlbumsResponse(val albums: List<AlbumResponse>?)


class Album(
    val albumId: String?,
    val albumName: String?,
    val artistName: String?,
    val coverUrl: String?,
    val releaseDate: String?,
    val tracks: List<Track>
)
