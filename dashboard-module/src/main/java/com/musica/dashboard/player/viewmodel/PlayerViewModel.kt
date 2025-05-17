package com.musica.dashboard.player.viewmodel

import android.app.Application
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.musica.common.service.models.response.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class PlayerViewModel(application: Application) : AndroidViewModel(application) {

    private var playlist: List<Track> = emptyList()
    private var currentIndex = 0
    private var shuffleEnabled = false
    private var repeatMode = RepeatMode.OFF

    private val player = ExoPlayer.Builder(getApplication()).build()
    private var updateJob: Job? = null

    private val _uiState = MutableStateFlow<PlayerUiState>(PlayerUiState.Loading)
    val uiState: StateFlow<PlayerUiState> = _uiState

    private val _playlistState = MutableStateFlow(PlaylistUiState(emptyList(), 0))
    val playlistState: StateFlow<PlaylistUiState> = _playlistState


    init {
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                startUpdatingUiState()
            }

            override fun onPlayerError(error: PlaybackException) {
                _uiState.value = PlayerUiState.Error(error.localizedMessage ?: "Playback error")
            }

            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    playNext()
                }
            }

        })
    }

    private fun startUpdatingUiState() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            while (true) {
                val title = player.currentMediaItem?.mediaMetadata?.title?.toString() ?: ""
                val trackArtist = player.currentMediaItem?.mediaMetadata?.artist?.toString() ?: ""
                val coverUrl = player.currentMediaItem?.mediaMetadata?.artworkUri?.toString() ?: ""
                val duration = player.duration.takeIf { it > 0 } ?: 0L
                val position = player.currentPosition
                val isPlaying = player.isPlaying

                _uiState.value = PlayerUiState.Playing(
                    title = title,
                    trackArtist = trackArtist,
                    coverUrl = coverUrl,
                    isPlaying = isPlaying,
                    duration = duration,
                    position = position,
                    shuffleEnabled = shuffleEnabled,
                    repeatMode = repeatMode
                )

                delay(500L)
            }
        }
    }

    fun playPlaylist(trackList: List<Track>) {
        playlist = trackList
        currentIndex = 0
        _playlistState.value = PlaylistUiState(
            tracks = trackList,
            currentIndex = currentIndex
        )

        loadTrackAt(currentIndex)
    }

    fun playNext() {
        if (currentIndex + 1 < playlist.size) {
            currentIndex++
            loadTrackAt(currentIndex)
        }
    }

    fun playPrevious() {
        if (player.currentPosition > 5000) {
            player.seekTo(0)
        } else if (currentIndex > 0) {
            currentIndex--
            loadTrackAt(currentIndex)
        }
    }

    fun toggleShuffle() {
        shuffleEnabled = !shuffleEnabled
        applyPlayerModes()
    }

    fun cycleRepeatMode() {
        repeatMode = when (repeatMode) {
            RepeatMode.OFF -> RepeatMode.ALL
            RepeatMode.ALL -> RepeatMode.ONE
            RepeatMode.ONE -> RepeatMode.OFF
        }
        applyPlayerModes()
    }


    private fun applyPlayerModes() {
        player.repeatMode = when (repeatMode) {
            RepeatMode.OFF -> Player.REPEAT_MODE_OFF
            RepeatMode.ONE -> Player.REPEAT_MODE_ONE
            RepeatMode.ALL -> Player.REPEAT_MODE_ALL
        }

        player.shuffleModeEnabled = shuffleEnabled
    }


    private fun loadTrackAt(index: Int) {
        if (index !in playlist.indices) return

        val track = playlist[index]

        val mediaItem = MediaItem.Builder()
            .setUri(track.trackUrl?.toUri())
            .setMediaMetadata(
                MediaMetadata.Builder().setTitle(track.trackName).build()
            ).build()

        player.setMediaItem(mediaItem)

        applyPlayerModes()

        player.prepare()
        player.playWhenReady = true
        startUpdatingUiState()
    }


//    fun play(uri: String, title: String) {
//        viewModelScope.launch {
//            _uiState.value = PlayerUiState.Loading
//            val mediaItem = MediaItem.Builder()
//                .setUri(uri.toUri())
//                .setMediaMetadata(
//                    MediaMetadata.Builder()
//                        .setTitle(title)
//                        .build()
//                ).build()
//            player.setMediaItem(mediaItem)
//            player.prepare()
//            player.playWhenReady = true
//        }
//    }

    fun togglePlayPause() {
        player.playWhenReady = !player.isPlaying
    }

    fun seekTo(position: Long) {
        player.seekTo(position)
    }


    override fun onCleared() {
        super.onCleared()
        updateJob?.cancel()
        player.release()
    }

}

data class PlaylistUiState(
    val tracks: List<Track>,
    val currentIndex: Int
)

sealed class PlayerUiState {
    object Loading : PlayerUiState()
    data class Playing(
        val title: String,
        val trackArtist: String,
        val coverUrl: String,
        val isPlaying: Boolean,
        val duration: Long,
        val position: Long,
        val shuffleEnabled: Boolean,
        val repeatMode: RepeatMode,
    ) : PlayerUiState()
    data class Error(val message: String) : PlayerUiState()
}

enum class RepeatMode {
    OFF, ONE, ALL
}
