package com.musica.common.navigation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musica.common.R
import com.musica.common.animations.SlideInAnimationVisibility
import com.musica.common.compose.theme.MusicaphoneTheme
import com.musica.common.compose.theme.Secondary
import com.musica.common.compose.theme.Tertiary

@Composable
fun MusicaNavBar() {
    Box(modifier = Modifier
        .background(color = Tertiary)

    ){

        Row(
            Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        start = Offset(0f, 0f),
                        end = Offset.Infinite,
                        colors = listOf(
                            Color(0xFF1F2427),
                            Color(0xFF485057)
                        )
                    ),
                    shape = RoundedCornerShape(32.dp)
                )
                .shadow(10.dp, ambientColor = Secondary, shape = RoundedCornerShape(32.dp)),
        ) {
            NavBarItem(
                icon = painterResource(id = R.drawable.home_nav_icon),
                text = stringResource(id = R.string.music_nav_bar_home_text),
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .weight(1f)
            )
            NavBarItem(
                icon = painterResource(id = R.drawable.search_nav_icon),
                text = stringResource(id = R.string.music_nav_bar_search_text),
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .weight(1f)
            )
            NavBarItem(
                icon = painterResource(id = R.drawable.library_nav_icon),
                text = stringResource(id = R.string.music_nav_bar_your_library_text),
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .weight(1f)
            )
            NavBarItem(
                icon = painterResource(id = R.drawable.premium_nav_icon),
                text = stringResource(id = R.string.music_nav_bar_premium_text),
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .weight(1f)
            )
        }
    }
}

@Composable
fun NavBarItem(
    icon: Painter,
    text: String,
    modifier: Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = icon,
            contentDescription = stringResource(id = R.string.music_nav_bar_navigation_icon_description_text),
            Modifier
                .width(20.dp)
                .height(20.dp),
            colorFilter = ColorFilter.tint(Secondary)
        )
        Text(
            text = text,
            fontSize = 10.sp,
            color = Secondary,
            textAlign = TextAlign.Center
        )
    }

}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MusicaphoneTheme {
        Surface {
            MusicaNavBar( )
        }
    }
}