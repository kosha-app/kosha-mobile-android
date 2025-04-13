package com.musica.dashboard


import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.musica.common.compose.Exclude
import com.musica.common.compose.KoshaComposeActivity
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.compose.theme.Tertiary
import com.musica.common.navigation.BottomNavItem
import com.musica.common.navigation.KoshaBottomNav
import com.musica.dashboard.album.ui.AlbumScreen
import com.musica.dashboard.artist.ui.ArtistScreen
import com.musica.dashboard.home.HomeScreen
import com.musica.dashboard.home.viewmodel.DashboardViewModel
import com.musica.dashboard.player.MusicPlayer
import com.musica.dashboard.player.KoshaPlayerBar
import com.musica.dashboard.player.viewmodel.PlayerViewModel
import com.musica.dashboard.search.ui.SearchScreen
import com.musica.dashboard.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask


@AndroidEntryPoint
class DashboardActivity : KoshaComposeActivity() {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    @Exclude
    override fun ActivityContent() {
        val navController = rememberNavController()

        var bottomBarShow by remember { mutableStateOf(true) }
        val bottomSheetHeight = remember { mutableStateOf(Dp.Unspecified) }
        val sheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Collapsed,
            animationSpec = tween(durationMillis = 500),
            confirmStateChange = { newState ->
                bottomSheetHeight.value = if (newState == BottomSheetValue.Expanded) {
                    718.dp
                } else {
                    Dp.Unspecified
                }
                true
            }
        )
        val scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = sheetState
        )

        var isBottomSheetExpanded by remember {
            mutableStateOf(false)
        }

        val scope = rememberCoroutineScope()

        val dashboardViewModel: DashboardViewModel = viewModel()
        val searchViewModel: SearchViewModel = viewModel()
        val playerV2ViewModel: PlayerViewModel = viewModel()

        val playUiState by playerV2ViewModel.uiState.collectAsState()

        BottomSheetScaffold(
            sheetContent = {
                MusicPlayer(
                    playUiState = playUiState,
                    onShuffleOnClick = {
//                        playerViewModel.shufflePlaylist()
                        playerV2ViewModel.toggleShuffle()
                    },
                    onPreviousOnClick = {
//                        playerViewModel.playPreviousTrack()
                        playerV2ViewModel.playPrevious()
                    },
                    onPlayPauseOnClick = {
                        playerV2ViewModel.togglePlayPause()
                    },
                    onNextOnClick = {
//                        playerViewModel.playNextTrack()
                        playerV2ViewModel.playNext()
                    },
                    onRepeatOnClick = { /*TODO*/ },
                    onBackClick = {
                        scope.launch { sheetState.collapse() }
                    },
                    seekTo = { seekToFloat ->
//                        playerViewModel.seekTo(seekToFloat.toInt())
                        playerV2ViewModel.seekTo(seekToFloat.toLong())
                    })

            }, scaffoldState = scaffoldState,
            sheetPeekHeight = 0.dp
        ) {
            Scaffold(
                bottomBar = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        KoshaPlayerBar(
                            playUiState = playUiState,
                            modifier = Modifier
                                .shadow(
                                    elevation = 100.dp,
                                )
                                .background(
                                    color = Tertiary,
                                ),
                            onPlayPauseClick = {
//                                playerViewModel.playPauseTrack()
                                playerV2ViewModel.togglePlayPause()
                            },
                            onBottomBarClick = {
                                bottomBarShow = !bottomBarShow
                                scope.launch {
                                    sheetState.expand()
                                }
                            },
                            seekTo = { seekFloat ->
//                                playerViewModel.seekTo(seekFloat.toInt())
                                playerV2ViewModel.seekTo(seekFloat.toLong())
                            }
                        )
                        KoshaBottomNav(navController = navController)
                    }
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .background(brush = Brush.verticalGradient(BackgroundGradientColors))
                ) {
                    NavHost(navController, startDestination = BottomNavItem.Home.route) {
                        composable(BottomNavItem.Home.route) {
                            HomeScreen(
                                viewModel = dashboardViewModel,
                                navController = navController
                            )
                        }
                        composable(BottomNavItem.Search.route) {
                            SearchScreen(
                                playerV2ViewModel = playerV2ViewModel,
                                searchViewModel = searchViewModel,
                                navController = navController,
                                onBackPressed = { navController.popBackStack() }
                            )
                        }
//                        composable(BottomNavItem.Premium.route) {
//                            //TODO to implement Premium Screen
//                        }
                        composable(BottomNavItem.YourLibrary.route) {
                            //TODO to implement Library Screen
                        }

                        composable("$ARTIST_SCREEN/{artistName}") {
                            val artist = it.arguments?.getString("artistName")
                            ArtistScreen(
                                playerV2ViewModel = playerV2ViewModel,
                                artistName = artist.toString(),
                                viewModel = dashboardViewModel,
                                navController = navController
                            )
                        }

                        composable("$ALBUM_SCREEN/{albumId}") {
                            val albumId = it.arguments?.getString("albumId")
                            AlbumScreen(
                                albumId = albumId,
                                dashboardViewModel = dashboardViewModel,
                                onBackPressed = { navController.popBackStack() },
                                navController = navController,
                                playerV2ViewModel = playerV2ViewModel
                            )
                        }
                    }
                }
            }
        }

        LaunchedEffect(Unit) {
            launch {
                isBottomSheetExpanded = sheetState.isExpanded
            }
        }

        BackHandler {
            if (sheetState.isExpanded) {
                scope.launch {
                    sheetState.collapse()
                }
            } else {
                finishAffinity()
            }
        }
    }

    companion object {
        const val ALBUM_SCREEN = "ArtistScreen"
        const val ARTIST_SCREEN = "AlbumScreen"
    }
}