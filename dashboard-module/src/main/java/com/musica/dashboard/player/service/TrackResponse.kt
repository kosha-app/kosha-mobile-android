package com.musica.dashboard.player.service

class TrackResponse(
    val id: String?,
    val trackName: String?,
    val trackArtist: String?,
    val trackUrl: String?,
    val coverUrl: String?
) {
    companion object {
        fun TrackResponse.toTrack(): Track {
            return Track(
                trackId = id,
                trackName = trackName,
                trackArtist = trackArtist,
                trackUrl = trackUrl,
                coverUrl = coverUrl
            )
        }
    }
}


class SearchTracksResponse(val tracks: List<TrackResponse>?)


data class Track(
    val trackId: String?,
    val trackName: String?,
    val trackArtist: String?,
    val trackUrl: String?,
    val coverUrl: String?
)