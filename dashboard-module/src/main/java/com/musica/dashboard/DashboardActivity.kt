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
import com.musica.dashboard.player.KoshaMusicPlayerViewModel
import com.musica.dashboard.player.MusicPlayer
import com.musica.dashboard.player.KoshaPlayerBar
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
        val playerViewModel: KoshaMusicPlayerViewModel = viewModel()


        val isPlaying by playerViewModel.isPlaying.collectAsState()
        val isPaused by playerViewModel.isPaused.collectAsState()
        var currentPosition by remember { mutableIntStateOf(0) }
        val duration by playerViewModel.duration.collectAsState()

        println("Sage Duration: $duration")

        val currentPlayingTrack by playerViewModel.currentPlayingTrack.collectAsState()

        BottomSheetScaffold(
            sheetContent = {
                MusicPlayer(
                    trackName = currentPlayingTrack.trackName.toString(),
                    trackArtist = currentPlayingTrack.trackArtist.toString(),
                    coverUrl = currentPlayingTrack.coverUrl.toString(),
                    isPlaying = isPlaying,
                    onShuffleOnClick = { /*TODO*/ },
                    onPreviousOnClick = { /*TODO*/ },
                    onPlayPauseOnClick = { playerViewModel.playPauseTrack() },
                    onNextOnClick = { playerViewModel.playNextTrack() },
                    onRepeatOnClick = { /*TODO*/ },
                    onBackClick = {
                        scope.launch { sheetState.collapse() }
                    },
                    currentPosition = currentPosition.toFloat(),
                    duration = duration.toFloat(),
                    seekTo = { seekToFloat ->
                        playerViewModel.seekTo(seekToFloat.toInt())
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
                        if (isPlaying || isPaused) {
                            KoshaPlayerBar(
                                modifier = Modifier
                                    .shadow(
                                        elevation = 100.dp,
                                    )
                                    .background(
                                        color = Tertiary,
                                    ),
                                onPlayPauseClick = { playerViewModel.playPauseTrack() },
                                currentPosition = currentPosition.toFloat(),
                                duration = duration.toFloat(),
                                trackName = currentPlayingTrack.trackName.toString(),
                                trackArtist = currentPlayingTrack.trackArtist.toString(),
                                coverUrl = currentPlayingTrack.coverUrl.toString(),
                                isPlaying = isPlaying,
                                onBottomBarClick = {
                                    bottomBarShow = !bottomBarShow
                                    scope.launch {
                                        sheetState.expand()
                                    }
                                },
                                seekTo = { seekFloat ->
                                    playerViewModel.seekTo(seekFloat.toInt())
                                }
                            )
                        }
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
                                playerViewModel = playerViewModel,
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
                                playerViewModel = playerViewModel,
                                artistName = artist.toString(),
                                viewModel = dashboardViewModel,
                                navController = navController
                            )
                        }

                        composable("$ALBUM_SCREEN/{albumId}") {
                            val albumId = it.arguments?.getString("albumId")
                            AlbumScreen(
                                playerViewModel = playerViewModel,
                                albumId = albumId,
                                dashboardViewModel = dashboardViewModel,
                                onBackPressed = { navController.popBackStack() },
                                navController = navController
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
            launch {
                Timer().scheduleAtFixedRate(object : TimerTask() {
                    override fun run() {
                        try {
                            currentPosition = playerViewModel.getCurrentPosition()
                        } catch (_: Exception) {
                        }
                    }
                }, 0, 1000)
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