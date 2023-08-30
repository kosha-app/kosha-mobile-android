package com.musica.dashboard.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.compose.theme.Primary
import com.musica.common.compose.theme.Secondary
import com.musica.common.compose.theme.Tertiary
import com.musica.dashboard.player.DashboardTopBar
import com.musica.common.navigation.MusicaNavBar
import com.musica.dashboard.player.MusicPlayer
import com.musica.dashboard.player.MusicaPlayerBar
import com.musica.dashboard.player.RecentlyPlayedCard
import com.musica.dashboard.player.service.TrackResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DashboardScreen(
    sheetState: BottomSheetState,
    scope: CoroutineScope,
    albumName: String,
    albumTracks: List<TrackResponse>,
    coverUrl: String,
    recentlyPlayedCardImageUrl: String,
    recentlyPlayedText: String,
    currentPosition: Float,
    duration: Float,
    isPlaying: Boolean,
    omSettingsClick: () -> Unit,
    onShuffleOnClick: () -> Unit,
    onPreviousOnClick: () -> Unit,
    onPlayPauseOnClick: () -> Unit,
    onNextOnClick: () -> Unit,
    onRepeatOnClick: () -> Unit,
    onItemClick: (trackId: String) -> Unit,
    seekTo: (millieSec: Float) -> Unit,
){

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    val trackName = remember { mutableStateOf("") }
    val trackArtist = remember { mutableStateOf("") }

    var bottomBarShow by remember { mutableStateOf(true) }


    BottomSheetScaffold(
        sheetContent = {
            MusicPlayer(
                trackName = trackName.value,
                trackArtist = trackArtist.value,
                coverUrl = coverUrl,
                isPlaying = isPlaying,
                onShuffleOnClick = onShuffleOnClick,
                onPreviousOnClick = onPreviousOnClick,
                onPlayPauseOnClick = onPlayPauseOnClick,
                onNextOnClick = onNextOnClick,
                onRepeatOnClick = onRepeatOnClick,
                onBackClick = {
                    scope.launch { sheetState.collapse() }
                },
                currentPosition = currentPosition,
                duration = duration,
                seekTo = {
                    seekTo(it)
                })

        }, scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp
    ) {
        Scaffold(modifier = Modifier.padding(), topBar = {
            DashboardTopBar(
                omSettingsClick = omSettingsClick
            )
        }, bottomBar = {
            MusicaNavBar()
        }) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .background(color = Color(0xFF353A40))
                    .fillMaxSize()
            ) {
                Column(
                    Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .background(brush = Brush.verticalGradient(BackgroundGradientColors)),
                ) {
                    val list = listOf(
                        Pair(recentlyPlayedCardImageUrl, recentlyPlayedText),
                        Pair(recentlyPlayedCardImageUrl, recentlyPlayedText),
                        Pair(recentlyPlayedCardImageUrl, recentlyPlayedText),
                        Pair(recentlyPlayedCardImageUrl, recentlyPlayedText),
                        Pair(recentlyPlayedCardImageUrl, recentlyPlayedText)
                    )
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "Recently Played $albumName",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    LazyRow(Modifier.fillMaxWidth()) {
                        items(items = list, itemContent = { item ->
                            RecentlyPlayedCard(
                                modifier = Modifier.padding(
                                    start = 16.dp, top = 20.dp, bottom = 40.dp
                                ), cardImageUrl = item.first, recentlyPlayText = item.second
                            )
                        })
                    }

                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "Recently Played $albumName",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    LazyRow(Modifier.fillMaxWidth()) {
                        items(items = albumTracks, itemContent = { item ->
                            DashboardItem(modifier = Modifier.padding(
                                start = 16.dp,
                                top = 20.dp,
                                bottom = 40.dp
                            ),
                                imageUrl = coverUrl,
                                header = item.trackArtist,
                                description = item.trackName ?: "",
                                onItemClick = {
                                    trackName.value = item.trackName.toString()
                                    trackArtist.value = item.trackArtist.toString()
                                    onItemClick(item.id.toString())
                                })
                        })
                    }

                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "Recently Played $albumName",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    LazyRow(Modifier.fillMaxWidth()) {
                        items(items = albumTracks, itemContent = { item ->
                            DashboardItem(
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    top = 20.dp,
                                    bottom = 40.dp
                                ),
                                imageUrl = coverUrl,
                                header = item.trackArtist,
                                description = item.trackName ?: "",
                            ) {}
                        })
                    }

                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "Recently Played $albumName",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    LazyRow(Modifier.fillMaxWidth()) {
                        items(items = albumTracks, itemContent = { item ->
                            DashboardItem(
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    top = 20.dp,
                                    bottom = 40.dp
                                ),
                                imageUrl = coverUrl,
                                header = item.trackArtist,
                                description = item.trackName ?: "",
                            ) {}
                        })
                    }
                }

                MusicaPlayerBar(modifier = Modifier
                    .shadow(
                        elevation = 100.dp,
                    )
                    .background(
                        color = Tertiary,
                    ),
                    onPlayPauseClick = onPlayPauseOnClick,
                    currentPosition = currentPosition,
                    duration = duration,
                    trackName = trackName.value,
                    trackArtist = trackArtist.value,
                    coverUrl = coverUrl,
                    isPlaying = isPlaying,
                    onBottomBarClick = {
                        bottomBarShow = !bottomBarShow
                        scope.launch {
                            sheetState.expand()
                        }
                    },
                    seekTo = {
                        seekTo(it)
                    })

            }
        }


    }
}