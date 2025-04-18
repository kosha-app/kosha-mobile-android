package com.musica.dashboard.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.musica.common.R
import com.musica.common.compose.Exclude
import com.musica.common.compose.theme.Primary
import com.musica.common.compose.theme.Secondary
import com.musica.dashboard.R.string
import com.musica.dashboard.player.viewmodel.PlayerUiState

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
@Exclude
fun KoshaPlayerBar(
    playUiState: PlayerUiState,
    modifier: Modifier,
    onPlayPauseClick: () -> Unit,
    seekTo: (milliSec: Float) -> Unit,
    onBottomBarClick: () -> Unit

) {

    var isPlaying by remember { mutableStateOf(false) }
    var duration by remember { mutableLongStateOf(0L) }
    var position by remember { mutableLongStateOf(0L) }
    var title by remember { mutableStateOf("") }
    var coverUrl by remember { mutableStateOf("") }
    var artist by remember { mutableStateOf("") }

    when(playUiState) {
        is PlayerUiState.Playing -> {
            isPlaying = playUiState.isPlaying
            duration = playUiState.duration
            position = playUiState.position
            title = playUiState.title
            coverUrl = playUiState.coverUrl
            artist = playUiState.trackArtist
        }
        else -> {}
    }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .clickable(onClick = onBottomBarClick)
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                GlideImage(
                    model = coverUrl,
                    contentDescription = stringResource(id = R.string.music_nav_bar_navigation_icon_description_text),
                    modifier = Modifier
                        .shadow(
                            elevation = 5.dp,
                            shape = RoundedCornerShape(28.dp)
                        )
                        .height(42.dp)
                        .width(42.dp)
                        .background(
                            color = Primary,
                            shape = RoundedCornerShape(28.dp)
                        )
                )
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                    Text(
                        text = artist,
                        color = Secondary,
                        fontSize = 12.sp
                    )
                }

                PlayerControls(
                    modifier = Modifier,
                    isPlaying = isPlaying,
                    volumeOnClick = { TODO() },
                    pausePlayOnClick = onPlayPauseClick,
                    favouriteOnClick = { TODO() }
                )
            }

            MusicSlider(
                position = position.toFloat(),
                duration = duration.toFloat(),
            ) {
                seekTo(it)
            }

        }
    }
}

@Composable
@Exclude
fun PlayerControls(
    modifier: Modifier,
    pausePlayOnClick: () -> Unit,
    favouriteOnClick: () -> Unit,
    volumeOnClick: () -> Unit,
    isPlaying: Boolean
) {

    Row(modifier = modifier) {
        Image(
            modifier = Modifier
                .size(18.dp)
                .clickable(onClick = {
                    pausePlayOnClick.invoke()
                }),
            painter  = if (!isPlaying) painterResource(id = R.drawable.play_icon) else painterResource(
                id = R.drawable.pause_icon
            ),
            contentDescription = stringResource(id = string.dashboard_play_pause_icon_description_text),
            colorFilter = ColorFilter.tint(
                Secondary
            ),
        )
        Image(
            painter = painterResource(id = R.drawable.heart_icon),
            contentDescription = stringResource(id = string.dashboard_play_favourite_icon_description_text),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clickable(onClick = favouriteOnClick)
        )
        Image(
            modifier = Modifier.clickable(onClick = volumeOnClick),
            painter = painterResource(id = R.drawable.mute_icon),
            contentDescription = stringResource(id = string.dashboard_play_mute_icon_description_text)
        )
    }
}
