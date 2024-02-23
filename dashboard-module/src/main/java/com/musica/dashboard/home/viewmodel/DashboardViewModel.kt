package com.musica.dashboard.home.viewmodel

import android.app.Application
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.musica.common.service.models.response.Album
import com.musica.common.service.models.response.AlbumResponse.Companion.toAlbum
import com.musica.common.service.models.response.Artist
import com.musica.common.service.models.response.ArtistPopularTracksResponse.Companion.toTrack
import com.musica.common.service.models.response.ResponseType
import com.musica.common.service.models.response.Track
import com.musica.dashboard.player.repository.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    application: Application,
    private val trackRepository: TrackRepository,
) : AndroidViewModel(application) {




    private val _album = MutableStateFlow<Album?>(null)
    private val _popularArtistsTracks = MutableStateFlow<List<Track>?>(listOf())
    private val _popularArtists = MutableStateFlow<List<Artist>>(listOf())
    private val _isGetAlbumLoading = MutableStateFlow(false)
    private val _isGetArtistInfoLoading = MutableStateFlow(false)

    val album: StateFlow<Album?> = _album.asStateFlow()
    val popularArtistsTracks: StateFlow<List<Track>?> = _popularArtistsTracks.asStateFlow()
    val popularArtists: StateFlow<List<Artist>> = _popularArtists.asStateFlow()
    val isGetAlbumLoading: StateFlow<Boolean> = _isGetAlbumLoading.asStateFlow()
    val isGetArtistInfoLoading: StateFlow<Boolean> = _isGetArtistInfoLoading.asStateFlow()

    init {
        viewModelScope.launch {
           val response = trackRepository.getPopularArtists()
            if (response.serviceResponse.responseType == ResponseType.SUCCESS && response.data != null){
                _popularArtists.value = response.data!!.artists
            }
        }
    }

    fun getAlbum(albumId: String) {
        _isGetAlbumLoading.value = true
        viewModelScope.launch {
            val response = trackRepository.getAlbum(albumId)

            if (response.serviceResponse.responseType == ResponseType.SUCCESS) {
                _album.value = response.data?.toAlbum()
            }
            _isGetAlbumLoading.value = false
        }
    }

    fun getPopularArtistsTracks(artistName: String) {
        _isGetArtistInfoLoading.value = true
        viewModelScope.launch {
            val response = trackRepository.getPopularArtistsTracks(artistName)

            if (response.serviceResponse.responseType == ResponseType.SUCCESS) {
                if (response.data != null) {
                    _popularArtistsTracks.value = response.data?.toTrack()
                }
            }
            _isGetArtistInfoLoading.value = false
        }
    }
}