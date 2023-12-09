package com.musica.dashboard.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musica.common.R
import com.musica.common.compose.RoundImage
import com.musica.common.compose.theme.KoshaTheme
import com.musica.common.compose.theme.Secondary
import com.musica.common.compose.theme.Tertiary25
import com.musica.common.compose.theme.White

@Composable
fun MusicPlayerTopBar(
    backOnClick: () -> Unit,
    optionsOnClick: () -> Unit,
    modifier: Modifier
) {

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            RoundImage(
                modifier = Modifier
                    .shadow(
                        elevation = 8.dp,
                        spotColor = Color.Black,
                        ambientColor = Color.Black,
                        shape = RoundedCornerShape(32.dp)
                    ),
                painter = painterResource(id = R.drawable.arrow_left),
                circleSize = 44.dp,
                imageSize = 24.dp,
                onClick = backOnClick
            )
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Playing From Playlist",
                    fontSize = 12.sp,
                    color = Secondary
                )
                Text(
                    text = "Mega Hit Mix",
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
            RoundImage(
                modifier = Modifier
                    .shadow(
                        elevation = 8.dp,
                        spotColor = Color.Black,
                        ambientColor = Color.Black,
                        shape = RoundedCornerShape(32.dp)
                    ),
                painter = painterResource(id = R.drawable.more_icon),
                circleSize = 44.dp,
                imageSize = 24.dp,
                onClick = optionsOnClick
            )

        }
    }

}

@Composable
fun KoshaTopBar(
    backOnClick: (() -> Unit)? = null,
    label: String,
    actions: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Tertiary25)
                .padding(vertical = 28.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (backOnClick != null) {
                RoundImage(
                    modifier = Modifier
                        .shadow(
                            elevation = 8.dp,
                            spotColor = Color.Black,
                            ambientColor = Color.Black,
                            shape = RoundedCornerShape(32.dp)
                        ),
                    painter = painterResource(id = R.drawable.arrow_left),
                    circleSize = 44.dp,
                    imageSize = 24.dp,
                    onClick = backOnClick
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(end = 48.dp),
                    text = label,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
            }
            actions?.invoke()
        }
    }

}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    KoshaTheme {
        KoshaTopBar(label = "Sage", backOnClick = {})

    }
}