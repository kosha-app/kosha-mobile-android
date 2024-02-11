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
    private val mediaPlayer: MediaPlayer
) : AndroidViewModel(application) {


    private val _album = MutableStateFlow<Album?>(null)
    private val _popularArtistsTracks = MutableStateFlow<List<Track>?>(listOf())
    private val _popularArtists = MutableStateFlow<List<Artist>>(listOf())
    private val _isPlaying = MutableStateFlow(false)
    private val _isPaused = MutableStateFlow(false)
    private val _isGetAlbumLoading = MutableStateFlow(false)
    private val _isGetArtistInfoLoading = MutableStateFlow(false)
    private val _duration = MutableStateFlow(0)

    val album: StateFlow<Album?> = _album.asStateFlow()
    val popularArtistsTracks: StateFlow<List<Track>?> = _popularArtistsTracks.asStateFlow()
    val popularArtists: StateFlow<List<Artist>> = _popularArtists.asStateFlow()
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()
    val isPaused: StateFlow<Boolean> = _isPaused.asStateFlow()
    val isGetAlbumLoading: StateFlow<Boolean> = _isGetAlbumLoading.asStateFlow()
    val isGetArtistInfoLoading: StateFlow<Boolean> = _isGetArtistInfoLoading.asStateFlow()
    val duration: StateFlow<Int> = _duration.asStateFlow()

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

    fun prepareTrack(trackId: String, trackUrl: String) {
        viewModelScope.launch {
            val response = trackRepository.trackPlayed(trackId)
            if (response.serviceResponse.responseType == ResponseType.SUCCESS) {
                try {
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer.reset()
                        mediaPlayer.setDataSource(trackUrl)
                        mediaPlayer.prepareAsync()
                        mediaPlayer.setOnPreparedListener { mediaPlayer ->
                            mediaPlayer.start()
                            _isPaused.value = false
                            _isPlaying.value = true
                            _duration.value = mediaPlayer.duration
                        }
                    } else {
                        mediaPlayer.stop()
                        mediaPlayer.reset()
                        mediaPlayer.setDataSource(trackUrl)
                        mediaPlayer.prepareAsync()
                        mediaPlayer.setOnPreparedListener { mediaPlayer ->
                            mediaPlayer.start()
                            _isPlaying.value = true
                            _duration.value = mediaPlayer.duration
                        }
                    }
                } catch (e : Exception) {
                    Log.e("DashboardViewModel",e.message.toString() )
                }
            }
        }
    }

    fun getCurrentPosition() = mediaPlayer.currentPosition

    fun playPauseTrack() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            _isPlaying.value = false
            _isPaused.value = true
        } else {
            mediaPlayer.start()
            _isPlaying.value = true
        }
    }

    fun stopTrack() {
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    fun seekTo(millieSec: Int) {
        mediaPlayer.seekTo(millieSec)
    }
}