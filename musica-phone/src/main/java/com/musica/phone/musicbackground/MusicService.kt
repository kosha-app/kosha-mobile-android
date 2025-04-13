package com.musica.phone.musicbackground

import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicService : MediaSessionService() {

    private lateinit var player: ExoPlayer
    private lateinit var mediaSession: MediaSession

    override fun onCreate() {
        super.onCreate()

        player = ExoPlayer.Builder(this).build()

        mediaSession = MediaSession.Builder(this, player)
            .setCallback(object : MediaSession.Callback {})
            .build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession {
        return mediaSession
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.release()
        player.release()
    }
}
