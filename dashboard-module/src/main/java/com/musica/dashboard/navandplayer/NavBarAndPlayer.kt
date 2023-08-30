package com.musica.dashboard.navandplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.musica.common.navigation.MusicaNavBar
import com.musica.common.compose.theme.Tertiary

@Composable
fun NavBarAndPlayer(onclick: () -> Unit) {

        Column(
            Modifier
                .background(color = Color(0xFF353A40))
                .clickable(onClick = onclick)
        ) {
            Spacer(modifier = Modifier
                .height(16.dp))
            Column(modifier = Modifier
                .shadow(elevation = 30.dp)
                .background(
                    color = Tertiary,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
            ) {

            }
        }
}
