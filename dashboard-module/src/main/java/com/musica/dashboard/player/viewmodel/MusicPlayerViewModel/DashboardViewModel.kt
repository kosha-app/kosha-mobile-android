package com.musica.dashboard.player.viewmodel.MusicPlayerViewModel

import android.app.Application
import android.media.MediaPlayer
import android.provider.Settings.Secure
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.musica.common.service.models.response.ResponseType
import com.musica.dashboard.player.repository.TrackRepository
import com.musica.dashboard.player.service.TrackResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
           val response = trackRepository.getAlbum("3bd144af-3b0d-4152-83ab-dbdbdb8c6ed0")

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