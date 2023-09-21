package com.musica.dashboard.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.musica.common.compose.MusicaComposeActivity
import com.musica.common.settings.SettingsActivity
import com.musica.dashboard.player.viewmodel.MusicPlayerViewModel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import kotlin.properties.Delegates


@AndroidEntryPoint
class DashboardActivity : MusicaComposeActivity() {

    private var isBottomSheetExpanded by Delegates.notNull<Boolean>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalMaterialApi::class)
    @SuppressLint("SuspiciousIndentation")
    @Composable
    override fun ActivityContent() {
        val context = LocalContext.current
        val viewModel: DashboardViewModel = viewModel()
        val bottomSheetHeight = remember { mutableStateOf(Dp.Unspecified) }
        val sheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Collapsed,
            animationSpec = tween(durationMillis = 500),
            confirmStateChange = {newState ->
                bottomSheetHeight.value = if (newState == BottomSheetValue.Expanded){
                    718.dp
                } else {
                    Dp.Unspecified
                }
                true
            }
        )

        val scope = rememberCoroutineScope()

        isBottomSheetExpanded = sheetState.isExpanded

        var currentPosition by remember { mutableIntStateOf(0) }
        val albumTracks by viewModel.albumTracks.collectAsState()
        val coverUrl by viewModel.albumCoverUrl.collectAsState()
        val isPlaying by viewModel.isPlaying.collectAsState()



        DashboardScreen(
            sheetState = sheetState,
            scope = scope,
            albumName = "",
            albumTracks = albumTracks,
            coverUrl = coverUrl,
            recentlyPlayedCardImageUrl = "",
            recentlyPlayedText = "",
            isPlaying = isPlaying,
            omSettingsClick = { startActivity(Intent(context, SettingsActivity::class.java)) },
            onShuffleOnClick = { /*TODO*/ },
            onPreviousOnClick = { /*TODO*/ },
            onPlayPauseOnClick = { viewModel.playPauseTrack() },
            onNextOnClick = { /*TODO*/ },
            onRepeatOnClick = { /*TODO*/ },
            currentPosition = currentPosition.toFloat(),
            duration = viewModel.getDuration().toFloat(),
            seekTo = {
                viewModel.seekTo(it.toInt())
            },
            onItemClick = { trackId ->
                for (track in albumTracks) {
                    println("Sage selected Nazo: $trackId")
                    if (trackId == track.id) {
                        viewModel.prepareTrack(track.trackUrl.toString())
                        println("Sage selected ${track.trackName} Nazo: $trackId track ${track.trackUrl}")
                    }
                }
            }
        )

        LaunchedEffect(viewModel) {
            Timer().scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    try {
                        currentPosition = viewModel.getCurrentPosition()
                    } catch (_: Exception) {
                    }
                }
            }, 0, 1000)

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



