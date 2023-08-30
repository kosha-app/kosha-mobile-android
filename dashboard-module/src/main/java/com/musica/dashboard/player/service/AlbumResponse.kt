package com.musica.dashboard.player.service

class AlbumResponse(
    val id: String?,
    val albumName: String?,
    val albumArtist: String?,
    val releaseDate: String?,
    val coverUrl: String?,
    val tracks: List<TrackResponse>,
    val message: String?
)

