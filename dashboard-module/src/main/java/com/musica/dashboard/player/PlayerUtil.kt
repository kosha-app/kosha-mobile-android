package com.musica.dashboard.player

class PlayerUtil {
    companion object{
        fun formatTime(duration: Float): String {
            val durationInSeconds = duration.toInt()/1000
            val minutes = durationInSeconds / 60
            val remainingSeconds = durationInSeconds % 60
            return "${minutes.toString().padStart(2, '0')}:${remainingSeconds.toString().padStart(2, '0')}"
        }
    }
}
