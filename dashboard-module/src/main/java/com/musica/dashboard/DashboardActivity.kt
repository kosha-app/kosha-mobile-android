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
import com.musica.common.compose.KoshaComposeActivity
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.compose.theme.Tertiary
import com.musica.common.navigation.BottomNavItem
import com.musica.common.navigation.KoshaBottomNav
import com.musica.dashboard.home.HomeScreen
import com.musica.dashboard.home.viewmodel.DashboardViewModel
import com.musica.dashboard.player.MusicPlayer
import com.musica.dashboard.player.MusicaPlayerBar
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


        val isPlaying by dashboardViewModel.isPlaying.collectAsState()
        var currentPosition by remember { mutableIntStateOf(0) }
        val duration by dashboardViewModel.duration.collectAsState()

        println("Sage Duration: $duration")

        var trackName by remember { mutableStateOf("") }
        var trackArtist by remember { mutableStateOf("") }
        var trackCoverUrl by remember { mutableStateOf("") }

        BottomSheetScaffold(
            sheetContent = {
                MusicPlayer(
                    trackName = trackName,
                    trackArtist = trackArtist,
                    coverUrl = trackCoverUrl,
                    isPlaying = isPlaying,
                    onShuffleOnClick = { /*TODO*/ },
                    onPreviousOnClick = { /*TODO*/ },
                    onPlayPauseOnClick = { dashboardViewModel.playPauseTrack() },
                    onNextOnClick = { /*TODO*/ },
                    onRepeatOnClick = { /*TODO*/ },
                    onBackClick = {
                        scope.launch { sheetState.collapse() }
                    },
                    currentPosition = currentPosition.toFloat(),
                    duration = duration.toFloat(),
                    seekTo = { seekToFloat ->
                        dashboardViewModel.seekTo(seekToFloat.toInt())
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
                        MusicaPlayerBar(
                            modifier = Modifier
                                .shadow(
                                    elevation = 100.dp,
                                )
                                .background(
                                    color = Tertiary,
                                ),
                            onPlayPauseClick = { dashboardViewModel.playPauseTrack() },
                            currentPosition = currentPosition.toFloat(),
                            duration = duration.toFloat(),
                            trackName = trackName,
                            trackArtist = trackArtist,
                            coverUrl = trackCoverUrl,
                            isPlaying = isPlaying,
                            onBottomBarClick = {
                                bottomBarShow = !bottomBarShow
                                scope.launch {
                                    sheetState.expand()
                                }
                            },
                            seekTo = { seekFloat ->
                                dashboardViewModel.seekTo(seekFloat.toInt())
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
                            )
                        }
                        composable(BottomNavItem.Search.route) {
                            SearchScreen(
                                searchViewModel = searchViewModel,
                                onTrackPlayClick = { name, artist, songUrl, picUrl ->
                                    trackName = name
                                    trackArtist = artist
                                    trackCoverUrl = picUrl

                                    dashboardViewModel.prepareTrack(songUrl)
                                }
                            )
                        }
                        composable(BottomNavItem.Premium.route) {
                            //TODO to implement Premium Screen
                        }
                        composable(BottomNavItem.YourLibrary.route) {
                            //TODO to implement Library Screen
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
                            currentPosition = dashboardViewModel.getCurrentPosition()
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
}