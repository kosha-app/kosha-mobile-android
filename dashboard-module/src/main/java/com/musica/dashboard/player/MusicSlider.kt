package com.musica.dashboard.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.musica.common.compose.theme.MusicaBlueColor
import com.musica.dashboard.home.ui.SliderInactive

@Composable
fun MusicSlider(
    currentPosition: Float,
    duration: Float,
    seekTo: (milliSec: Float) -> Unit
) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    Slider(
        modifier = Modifier
            .padding(top = 14.dp, bottom = 22.dp, start = 16.dp, end = 16.dp)
            .shadow(50.dp)
            .height(3.dp)
            .fillMaxWidth()
            .background(
                color = Color(0x08FFFFFF),
                shape = RoundedCornerShape(size = 30.dp)
            ),
        value = sliderPosition,
        onValueChange = {
            sliderPosition = it
            seekTo(sliderPosition)
        },
        valueRange = 0f..duration,
        colors = SliderDefaults.colors(
            thumbColor = MusicaBlueColor,
            activeTrackColor = MusicaBlueColor,
            inactiveTrackColor = SliderInactive,
        )
    )

    LaunchedEffect(currentPosition){
        sliderPosition = currentPosition
    }
}