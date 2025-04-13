package com.musica.dashboard.artist.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.musica.common.compose.Exclude
import com.musica.common.compose.RoundImage
import com.musica.common.compose.TopBar
import com.musica.common.compose.TrackItem
import com.musica.common.compose.dialog.ProgressScreen
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.compose.theme.KoshaTheme
import com.musica.common.compose.theme.MusicaBlueColor
import com.musica.common.compose.theme.Primary
import com.musica.common.compose.theme.Secondary
import com.musica.common.compose.theme.Tertiary
import com.musica.common.compose.theme.White
import com.musica.common.service.models.response.ArtistPopularTracksResponse.Companion.toTrack
import com.musica.dashboard.R
import com.musica.dashboard.home.viewmodel.DashboardViewModel
import com.musica.dashboard.player.viewmodel.PlayerViewModel

@Composable
@Exclude
fun ArtistScreen(
    playerV2ViewModel: PlayerViewModel,
    viewModel: DashboardViewModel,
    artistName: String,
    navController: NavController
) {

    val popularArtistsTracks by viewModel.popularArtistsTracks.collectAsState()
    val isLoading by viewModel.isGetArtistInfoLoading.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                title = artistName,
                onBackPressed = { navController.popBackStack() },
            )
        }
    ) {

        if (isLoading) {
            ProgressScreen()
        } else {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(BackgroundGradientColors)
                    )
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Image(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 24.dp)
                        .size(180.dp)
                        .background(color = Secondary, shape = RoundedCornerShape(26.dp)),
                    painter = painterResource(id = com.musica.common.R.drawable.artist_icon),
                    contentDescription = ""

                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    RoundImage(
                        painter = painterResource(id = com.musica.common.R.drawable.shuffle_icon),
                        imageSize = 24.dp,
                        circleSize = 45.dp
                    )
                    RoundImage(
                        modifier = Modifier.padding(start = 16.dp),
                        painter = painterResource(id = com.musica.common.R.drawable.play_icon),
                        imageSize = 24.dp,
                        circleSize = 45.dp,
                        onClick = {
                            popularArtistsTracks?.let { tracks ->
                                playerV2ViewModel.playPlaylist(tracks)
                            }
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp)
                        .background(color = Primary, shape = RoundedCornerShape(26.dp))
                        .padding(16.dp),
                ) {
                    Text(
                        text = "Popular",
                        color = White,
                        fontSize = 16.sp
                    )
                    popularArtistsTracks?.forEach { track ->
                        TrackItem(
                            coverUrl = track.coverUrl.toString(),
                            trackName = track.trackName.toString(),
                            trackArtist = track.trackArtist.toString(),
                            onTrackClick = {
                                playerV2ViewModel.playPlaylist(listOf(track))
                            },
                            trailing = {
                                Text(
                                    text = track.played.toString(),
                                    color = Secondary,
                                    fontSize = 14.sp
                                )
                            }
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp)
                        .background(color = Primary, shape = RoundedCornerShape(26.dp))
                        .padding(16.dp),
                ) {
                    Text(
                        text = "Popular Releases",
                        color = White,
                        fontSize = 16.sp
                    )
                    TrackItem(
                        coverUrl = "Cover",
                        trackName = "Track Name",
                        trackArtist = "Artist",
                        onTrackClick = {},
                        trailing = {
                            Text(
                                text = "10,000,000",
                                color = Secondary,
                                fontSize = 14.sp
                            )
                        }
                    )
                }
            }
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.getPopularArtistsTracks(artistName.trim())
    }
}


@Composable
@Preview
@Exclude
private fun ArtistScreenPreview() {
    KoshaTheme {
//        ArtistScreen()
    }
}