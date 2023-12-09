package com.musica.dashboard.player.service

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
                releaseData = releaseDate
            )
        }
    }
}

class SearchResponse(val albums: List<AlbumResponse>?)


class Album(
    val albumId: String?,
    val albumName: String?,
    val artistName: String?,
    val coverUrl: String?,
    val releaseData: String?
)
