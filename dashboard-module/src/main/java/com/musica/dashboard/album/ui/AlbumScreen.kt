package com.musica.dashboard.album.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.bumptech.glide.Glide
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
import com.musica.common.compose.theme.Secondary
import com.musica.common.compose.theme.White
import com.musica.dashboard.DashboardActivity.Companion.ARTIST_SCREEN
import com.musica.dashboard.home.viewmodel.DashboardViewModel
import com.musica.dashboard.player.KoshaMusicPlayerViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
@Exclude
fun AlbumScreen(
    playerViewModel: KoshaMusicPlayerViewModel,
    albumId: String?,
    dashboardViewModel: DashboardViewModel,
    onBackPressed: () -> Unit,
    navController: NavController
) {

    val isLoading by dashboardViewModel.isGetAlbumLoading.collectAsState()
    val album by dashboardViewModel.album.collectAsState()
    val albumCoverUrl = album?.coverUrl.toString()

    Scaffold(
        topBar = {
            TopBar(title = "", onBackPressed = onBackPressed)
        }
    ) {
        if (isLoading) {
            ProgressScreen()
        } else {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(brush = Brush.verticalGradient(BackgroundGradientColors))
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GlideImage(
                    modifier = Modifier
                        .size(250.dp)
                        .shadow(16.dp, shape = RoundedCornerShape(26.dp))
                        .background(MusicaBlueColor, shape = RoundedCornerShape(26.dp)),
                    model = albumCoverUrl,
                    contentDescription = "Album Cover"
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(
                        text = album?.albumName ?: "",
                        color = White
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = album?.artistName ?: "",
                        color = White,
                        fontSize = 12.sp
                    )
                    Text(
                        text = album?.releaseDate ?: "",
                        color = MusicaBlueColor,
                        fontSize = 8.sp
                    )

                }

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
                            album?.let { safeAlbum -> playerViewModel.preparePlaylist(safeAlbum.tracks) }
                        }
                    )
                }
                album?.tracks?.forEach { track ->
                    TrackItem(
                        coverUrl = albumCoverUrl,
                        trackName = track.trackName.toString(),
                        trackArtist = track.trackArtist.toString(),
                        onTrackClick = {
                            playerViewModel.preparePlaylist(listOf(track))
                        }
                    )
                }

                album?.artistName?.split(",")?.forEach { artistName ->
                    ArtistItem(
                        artistImageUrl = "",
                        artist = artistName,
                        onArtistClick = {
                            navController.navigate("$ARTIST_SCREEN/$artistName")
                        }
                    )
                }

                Text(
                    modifier = Modifier.padding(top = 24.dp),
                    text = "Record Label",
                    color = Secondary,
                    fontSize = 12.sp
                )
            }
        }
    }

    LaunchedEffect(dashboardViewModel) {
        if (albumId != null) {
            dashboardViewModel.getAlbum(albumId)
        }
    }
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class)
@Composable
@Exclude
private fun ArtistItem(
    artistImageUrl: String,
    artist: String,
    onArtistClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onArtistClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = artistImageUrl,
            contentDescription = "Artist Image"
        )
        ListItem(
            text = {
                Text(
                    text = artist,
                    color = White
                )
            }
        )
    }

}

@Composable
@Preview
private fun AlbumScreenPreview() {
    KoshaTheme {
//        AlbumScreen()
    }
}