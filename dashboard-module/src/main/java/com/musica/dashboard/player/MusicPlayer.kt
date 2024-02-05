package com.musica.dashboard.player

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.musica.common.R
import com.musica.common.compose.Exclude
import com.musica.common.compose.RoundImage
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.compose.theme.Secondary
import com.musica.common.compose.theme.Tertiary25
import com.musica.dashboard.R.string
import com.musica.dashboard.home.options.OptionsSheet
import com.musica.dashboard.player.lyrics.LyricsCard
import com.musica.dashboard.player.songinfo.InfoCard
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class,
    ExperimentalMaterialApi::class
)
@Composable
@Exclude
fun MusicPlayer(
    trackName: String,
    trackArtist: String,
    coverUrl: String,
    isPlaying: Boolean,
    onShuffleOnClick: () -> Unit,
    onPreviousOnClick: () -> Unit,
    onPlayPauseOnClick: () -> Unit,
    onNextOnClick: () -> Unit,
    onRepeatOnClick: () -> Unit,
    onBackClick: () -> Unit,
    currentPosition: Float,
    duration: Float,
    seekTo: (millieSec: Float) -> Unit
) {

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed, animationSpec = tween(durationMillis = 500)
    )

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetContent ={ OptionsSheet("", "Gazzet (Kazet)", trackArtist = "SageEM") },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp
    )
    {
        Scaffold(
            modifier = Modifier
                .padding(),
            topBar = {
                MusicPlayerTopBar(
                    modifier = Modifier.background(color = Tertiary25),
                    backOnClick = onBackClick,
                    optionsOnClick = {
                        scope.launch {
                            sheetState.expand()
                        }
                    }
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(
                        brush = Brush.verticalGradient(
                            colors = BackgroundGradientColors
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    Modifier
                        .padding(vertical = 80.dp)
                ) {

                    GlideImage(
                        model = coverUrl,
                        contentDescription = stringResource(id = string.music_player_cover_image_description),
                        modifier = Modifier
                            .width(280.dp)
                            .height(280.dp)
                            .shadow(
                                elevation = 50.dp,
                                ambientColor = Color.White,
                                shape = RoundedCornerShape(32.dp)
                            )
                            .background(
                                color = Color.Gray,
                                shape = RoundedCornerShape(
                                    32.dp
                                )

                            )
                    )
                }
                MusicPlayerTrackInfo(
                    trackName = trackName,
                    trackArtist = trackArtist,
                    currentPosition = currentPosition,
                    duration = duration,
                    seekTo = {
                        seekTo(it)
                    }
                )
                MusicPlayControls(
                    isPlaying = isPlaying,
                    onShuffleOnClick = onShuffleOnClick,
                    onPreviousOnClick = onPreviousOnClick,
                    onPlayPauseOnClick = onPlayPauseOnClick,
                    onNextOnClick = onNextOnClick,
                    onRepeatOnClick = onRepeatOnClick
                )
                MusicPlayerFooter()
                LyricsCard(
                    modifier = Modifier
                        .padding(
                            top = 40.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                        .shadow(24.dp))

                InfoCard(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 40.dp)
                        .shadow(24.dp),
                    headingText = "About The Artist",
                    descriptionHeader = "SagEM",
                    descriptionSubText = "23,545,456 monthly listeners",
                    buttonText = "Follow",
                    description = "An internet based vocalist, producer, writer, director and performance artist, Oliver Tree..."
                )

                InfoCard(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 30.dp)
                        .shadow(24.dp),
                    headingText = "Live Events",
                    descriptionHeader = "jun 9 - aug 25",
                    buttonText = "Find tickets",
                    descriptionSubText = "4 events on tour"
                )
            }
        }
    }
}

@Composable
@Exclude
fun MusicPlayerTrackInfo(
    trackName: String,
    trackArtist: String,
    currentPosition: Float,
    duration: Float,
    seekTo: (millieSec: Float) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            Modifier.weight(1f)
        ) {
            Text(
                text = trackName,
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold

            )
            Text(
                text = trackArtist,
                fontSize = 12.sp,
                color = Secondary
            )
        }
        Image(
            painter = painterResource(id = R.drawable.heart_icon),
            contentDescription = stringResource(id = string.dashboard_play_favourite_icon_description_text),
            colorFilter = ColorFilter.tint(Color.White)
        )
    }

    MusicSlider(
        currentPosition = currentPosition,
        duration = duration,
        seekTo = {seekTo(it)}
    )

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = PlayerUtil.formatTime(currentPosition),
            fontSize = 14.sp,
            color = Color.White,
        )
        Text(
            text = PlayerUtil.formatTime(duration),
            fontSize = 12.sp,
            color = Color.White
        )
    }
}

@Composable
@Exclude
fun MusicPlayControls(
    isPlaying: Boolean,
    onShuffleOnClick: () -> Unit,
    onPreviousOnClick: () -> Unit,
    onPlayPauseOnClick: () -> Unit,
    onNextOnClick: () -> Unit,
    onRepeatOnClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 34.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundImage(
            modifier = Modifier
                .shadow(
                    elevation = 8.dp,
                    spotColor = Secondary,
                    ambientColor = Secondary,
                    shape = RoundedCornerShape(32.dp)
                ),
            painter = painterResource(id = R.drawable.shuffle_icon),
            circleSize = 42.dp,
            imageSize = 18.dp,
            onClick =  onShuffleOnClick
        )
        RoundImage(
            modifier = Modifier
                .padding(start = 19.dp)
                .shadow(
                    elevation = 8.dp,
                    spotColor = Secondary,
                    ambientColor = Secondary,
                    shape = RoundedCornerShape(32.dp)
                ),
            painter = painterResource(id = R.drawable.previous_icon),
            circleSize = 52.dp,
            imageSize = 24.dp,
            onClick = onPreviousOnClick
        )
        RoundImage(
            modifier = Modifier
                .padding(horizontal = 19.dp)
                .shadow(
                    elevation = 8.dp,
                    spotColor = Secondary,
                    ambientColor = Secondary,
                    shape = RoundedCornerShape(32.dp)
                ),
            painter = if (!isPlaying) painterResource(id = R.drawable.play_icon) else painterResource(
                id = R.drawable.pause_icon
            ),
            circleSize = 62.dp,
            imageSize = 28.dp,
            onClick = onPlayPauseOnClick
        )
        RoundImage(
            modifier = Modifier
                .padding(end = 19.dp)
                .shadow(
                    elevation = 8.dp,
                    spotColor = Secondary,
                    ambientColor = Secondary,
                    shape = RoundedCornerShape(32.dp)
                ),
            painter = painterResource(id = R.drawable.next_icon),
            circleSize = 52.dp,
            imageSize = 24.dp,
            onClick = onNextOnClick
        )
        RoundImage(
            modifier = Modifier
                .shadow(
                    elevation = 8.dp,
                    spotColor = Secondary,
                    ambientColor = Secondary,
                    shape = RoundedCornerShape(32.dp)
                ),
            painter = painterResource(id = R.drawable.repeate_music_icon),
            circleSize = 42.dp,
            imageSize = 18.dp,
            onClick = onRepeatOnClick
        )
    }

}

@Composable
@Exclude
fun MusicPlayerFooter(){
    Row(
        Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.device_icon ),
            contentDescription = stringResource(id = string.music_player_device_icon_description)
        )

        Column(
            Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Text(
                text = stringResource(id = string.music_player_current_device_playing_header_text),
                fontSize = 14.sp,
                color = Color.White
            )
            Text(
                text = stringResource(id = string.music_player_current_device_playing_text),
                fontSize = 14.sp,
                color = Secondary
            )
        }

        Image(
            painter = painterResource(id = R.drawable.share_icon ),
            contentDescription = stringResource(id = string.music_player_share_icon_description))
        Image(
            painter = painterResource(id = R.drawable.sort_icon ),
            contentDescription = stringResource(id = string.music_player_more_device_icon_description),
            Modifier.padding(start = 24.dp)
        )
    }
}