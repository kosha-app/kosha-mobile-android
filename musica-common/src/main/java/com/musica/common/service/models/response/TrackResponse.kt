package com.musica.common.service.models.response

import com.musica.common.service.models.response.TrackResponse.Companion.toTrack

class TrackResponse(
    val id: String?,
    val trackName: String?,
    val trackArtist: String?,
    val trackFeatures: String?,
    val played: Int?,
    val trackUrl: String?,
    val coverUrl: String?
) {
    companion object {
        fun TrackResponse.toTrack(): Track {
            return if (!trackFeatures.isNullOrBlank()) {
                Track(
                    trackId = id,
                    trackName = "$trackName (feat. $trackFeatures)",
                    trackArtist = trackArtist,
                    trackUrl = trackUrl,
                    coverUrl = coverUrl,
                    played = played
                )
            } else {
                Track(
                    trackId = id,
                    trackName = trackName,
                    trackArtist = trackArtist,
                    trackUrl = trackUrl,
                    coverUrl = coverUrl,
                    played = played
                )
            }
        }
    }
}


class SearchTracksResponse(val tracks: List<TrackResponse>?)

data class TrackPlayedRequest(
    val albumId: String,
    val trackId: String
)

class ArtistPopularTracksResponse(val tracks: List<TrackResponse>?) {
    companion object {
        fun ArtistPopularTracksResponse.toTrack(): List<Track>? {
            return tracks?.map { track -> track.toTrack() }
        }
    }
}

class PopularArtistResponse(val artists: List<Artist>)

class Artist(
    val artistName: String,
    val totalStreams: Int
)

data class Track(
    val trackId: String?,
    val trackName: String?,
    val trackArtist: String?,
    val trackUrl: String?,
    val coverUrl: String?,
    val played: Int?,
)