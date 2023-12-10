package com.musica.dashboard.home

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.settings.SettingsActivity
import com.musica.dashboard.home.viewmodel.DashboardViewModel
import com.musica.dashboard.player.DashboardTopBar
import com.musica.dashboard.player.RecentlyPlayedCard

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: DashboardViewModel,
) {
    val context = LocalContext.current

    val trackName = remember { mutableStateOf("") }
    val trackArtist = remember { mutableStateOf("") }

    val albumTracks by viewModel.albumTracks.collectAsState()
    val coverUrl by viewModel.albumCoverUrl.collectAsState()
    val albumName = ""

    val recentlyPlayedCardImageUrl = ""
    val recentlyPlayedText = ""

    Scaffold(modifier = Modifier.padding(), topBar = {
        DashboardTopBar(
            omSettingsClick = {
                startActivity(context, Intent(context, SettingsActivity::class.java), Bundle())
            }
        )
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
                                for (track in albumTracks) {
                                    println("Sage selected Nazo: $item.id.toString()")
                                    if (item.id.toString() == track.id) {
                                        viewModel.prepareTrack(track.trackUrl.toString())
                                        println("Sage selected ${track.trackName} Nazo: ${item.id.toString()} track ${track.trackUrl}")
                                    }
                                }
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

        }
    }
}