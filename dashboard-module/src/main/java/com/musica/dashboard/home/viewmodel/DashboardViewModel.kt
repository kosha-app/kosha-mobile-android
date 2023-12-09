package com.musica.dashboard.home.viewmodel

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.musica.common.service.models.response.ResponseType
import com.musica.dashboard.player.repository.TrackRepository
import com.musica.dashboard.player.service.TrackResponse
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


    private val _albumName = MutableStateFlow("")
    private val _albumCoverUrl = MutableStateFlow("")
    private val _albumTracks = MutableStateFlow(listOf(TrackResponse("","","","")))
    private val _isPlaying= MutableStateFlow(false)

    val albumName: StateFlow<String> = _albumName.asStateFlow()
    val albumTracks: StateFlow<List<TrackResponse>> =_albumTracks.asStateFlow()
    val albumCoverUrl: StateFlow<String> = _albumCoverUrl.asStateFlow()
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()


    init {
        viewModelScope.launch {
           val response = trackRepository.getAlbum("d8184e4d-c08c-48df-a898-23a1699c490a")

            if (response.serviceResponse.responseType == ResponseType.SUCCESS){
                _albumName.value = response.data?.albumName ?: ""
                _albumTracks.value = response.data?.tracks ?: listOf()
                _albumCoverUrl.value = response.data?.coverUrl ?: ""
            }
            println("Sage: Track -- ${_albumTracks.value[0].id}")
        }

    }

    fun prepareTrack(trackUrl: String) {
        try {
            if (mediaPlayer.isPlaying){
                mediaPlayer.reset()
                mediaPlayer.setDataSource(trackUrl)
                mediaPlayer.prepareAsync()
                mediaPlayer.setOnPreparedListener { mediaPlayer ->
                    mediaPlayer.start()
                    _isPlaying.value = true
                }
            }else {
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.setDataSource(trackUrl)
                mediaPlayer.prepareAsync()
                mediaPlayer.setOnPreparedListener { mediaPlayer ->
                    mediaPlayer.start()
                    _isPlaying.value = true
                }
            }
        } catch (_: Exception) {

        }
    }

    fun isPlaying() = mediaPlayer.isPlaying

    fun getCurrentPosition() = mediaPlayer.currentPosition

    fun getDuration() = mediaPlayer.duration

    fun playPauseTrack(){
        if (mediaPlayer.isPlaying){
            mediaPlayer.pause()
//            mediaPlayer.release()
            println("Sage Track at Value: ${getCurrentPosition()}")
            _isPlaying.value = false
        }else{
            mediaPlayer.start()
            println("Sage Track at: ${mediaPlayer.currentPosition}")
            _isPlaying.value = true
        }
    }

    fun stopTrack(){
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    fun seekTo(millieSec: Int){
        mediaPlayer.seekTo(millieSec)
    }
}