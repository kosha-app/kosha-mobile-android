package com.musica.dashboard.player

import android.app.Application
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
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
class KoshaMusicPlayerViewModel @Inject constructor(
    application: Application,
    private val trackRepository: TrackRepository,
    private val mediaPlayer: MediaPlayer
) : AndroidViewModel(application) {

    private var isShuffle = false
    private var trackIndex = 0

    private val _playList = MutableStateFlow(listOf<Track>())
    private val _isPlaying = MutableStateFlow(false)
    private val _isPaused = MutableStateFlow(false)
    private val _duration = MutableStateFlow(0)
    private val _currentPlayingTrack = MutableStateFlow(Track("", "", "", "", "", 0))

    val playList: StateFlow< List<Track> > = _playList.asStateFlow()
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()
    val isPaused: StateFlow<Boolean> = _isPaused.asStateFlow()
    val duration: StateFlow<Int> = _duration.asStateFlow()
    val currentPlayingTrack: StateFlow<Track> = _currentPlayingTrack.asStateFlow()

    fun preparePlaylist(tracList: List<Track>) {
        _playList.value = tracList
        playTrack(trackId = _playList.value[0].trackId.toString(), trackUrl = _playList.value[0].trackUrl.toString())
    }

    fun playNextTrack() {
        if (isShuffle) {

        } else {
            if (trackIndex + 1 != _playList.value.size) {
                trackIndex += 1
                playTrack(trackId = _playList.value[trackIndex].trackId.toString(), trackUrl = _playList.value[trackIndex].trackUrl.toString())
            }
        }
    }

    private fun playTrack(trackId: String, trackUrl: String) {
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
                            _currentPlayingTrack.value = _playList.value[trackIndex]
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
                            _currentPlayingTrack.value = _playList.value[trackIndex]
                            mediaPlayer.start()
                            _isPlaying.value = true
                            _duration.value = mediaPlayer.duration
                        }
                    }
                } catch (e: Exception) {
                    Log.e("DashboardViewModel", e.message.toString())
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