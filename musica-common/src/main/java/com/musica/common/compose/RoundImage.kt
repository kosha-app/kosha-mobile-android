package com.musica.common.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.musica.common.compose.theme.Primary

@Composable
fun RoundImage(
    modifier: Modifier = Modifier,
    painter: Painter,
    imageSize: Dp,
    circleSize: Dp,
    onClick: () -> Unit = {}
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .background(
                    color = Primary,
                    shape = RoundedCornerShape(32.dp)
                )
                .width(circleSize)
                .height(circleSize)
                .clickable(onClick = onClick),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painter,
                contentDescription = "Circular Button",
                modifier = Modifier
                    .width(imageSize)
                    .height(imageSize),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }


}