package com.musica.dashboard.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.musica.common.R
import com.musica.common.compose.Exclude
import com.musica.common.compose.input.InputText
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.compose.theme.DarkGrey
import com.musica.common.compose.theme.KoshaTheme
import com.musica.common.compose.theme.Secondary
import com.musica.common.compose.theme.Tertiary25
import com.musica.common.compose.theme.White
import com.musica.dashboard.DashboardActivity.Companion.ALBUM_SCREEN
import com.musica.dashboard.player.KoshaMusicPlayerViewModel
import com.musica.dashboard.player.KoshaTopBar
import com.musica.dashboard.search.viewmodel.SearchViewModel

@Composable
@Exclude
fun SearchScreen(
    searchViewModel: SearchViewModel,
    playerViewModel: KoshaMusicPlayerViewModel,
    navController: NavController,
    onBackPressed: () -> Unit
) {

    var searchQueryInput by remember { mutableStateOf("") }

    val albums by searchViewModel.albums.collectAsState()
    val tracks by searchViewModel.tracks.collectAsState()

    Scaffold(
        topBar = {
            KoshaTopBar(
                label = "Search",
                backOnClick = onBackPressed,
                modifier = Modifier
                    .background(color = Tertiary25)
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(BackgroundGradientColors)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            InputText(
                modifier = Modifier.padding(horizontal = 16.dp),
                value = searchQueryInput,
                placeholder = "What do you want to listen to?",
                onValueChange = {
                    searchQueryInput = it
                    searchViewModel.search(it)
                },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.search_nav_icon),
                        contentDescription = "Search Icon",
                        colorFilter = ColorFilter.tint(White)
                    )
                }
            )

            if (searchQueryInput != "") {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                    LazyColumn(content = {
                        items(items = tracks) { item ->
                            SearchedItems(
                                itemPictureUrl = item.coverUrl.toString(),
                                label = item.trackName.toString(),
                                subLabel = "Song - ${item.trackArtist}",
                                onClick = {
                                    playerViewModel.preparePlaylist(listOf(item))
                                }
                            )
                        }
                    })

                    LazyColumn(content = {
                        items(items = albums) { album ->
                            SearchedItems(
                                itemPictureUrl = album.coverUrl.toString(),
                                label = album.albumName.toString(),
                                subLabel = "Album - ${album.artistName}",
                                onClick = {
                                    navController.navigate("$ALBUM_SCREEN/${album.albumId}")
                                }
                            )
                        }
                    })

                }

            } else {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier.padding(top = 32.dp, start = 16.dp),
                        text = "Browse all",
                        fontSize = 16.sp,
                        color = White
                    )
                    val data = listOf(
                        "Podcast",
                        "Live Events",
                        "Made for you",
                        "New Releases",
                        "Hindi",
                        "House",
                        "Amapiano",
                        "Hip Hop",
                        "Podcast",
                        "Live Events",
                        "Made for you",
                        "New Releases",
                        "Hindi",
                        "House",
                        "Amapiano",
                        "Hip Hop"
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        content = {
                            items(items = data) { item ->
                                SearchItems(
                                    itemPictureUrl = "",
                                    label = item
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
@Exclude
private fun SearchItems(
    itemPictureUrl: String,
    label: String
) {

    Row(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .shadow(
                elevation = 4.dp,
                ambientColor = Color.Black,
                shape = RoundedCornerShape(26.dp)
            )
            .background(
                color = DarkGrey,
                shape = RoundedCornerShape(
                    26.dp
                )
            )
            .width(140.dp)
            .height(55.dp)
    ) {
        GlideImage(
            model = itemPictureUrl,
            contentDescription = stringResource(id = com.musica.dashboard.R.string.music_player_cover_image_description),
            modifier = Modifier
                .width(56.dp)
                .height(56.dp)
                .background(
                    color = Color.Gray,
                    shape = RoundedCornerShape(
                        26.dp
                    )

                )
        )

        Text(
            modifier = Modifier.padding(top = 21.dp, bottom = 21.dp, start = 12.dp),
            text = label,
            fontSize = 12.sp,
            color = White
        )
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
@Exclude
private fun SearchedItems(
    itemPictureUrl: String,
    label: String,
    subLabel: String,
    onClick: () -> Unit
) {


    Row(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .shadow(
                elevation = 4.dp,
                ambientColor = Color.Black,
                shape = RoundedCornerShape(26.dp)
            )
            .background(
                color = DarkGrey,
                shape = RoundedCornerShape(
                    26.dp
                )
            )
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        GlideImage(
            model = itemPictureUrl,
            contentDescription = stringResource(id = com.musica.dashboard.R.string.music_player_cover_image_description),
            modifier = Modifier
                .width(56.dp)
                .height(56.dp)
                .background(
                    color = Color.Gray,
                    shape = RoundedCornerShape(
                        26.dp
                    )

                )
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .height(56.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                fontSize = 16.sp,
                color = White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subLabel,
                fontSize = 14.sp,
                color = Secondary,
                fontWeight = FontWeight.SemiBold
            )
        }

    }

}

@Composable
@Exclude
@Preview
private fun SearchScreenPreview() {
    KoshaTheme {
//        SearchScreen()
    }
}