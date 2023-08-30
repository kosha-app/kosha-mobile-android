package com.musica.dashboard.player.lyrics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musica.common.compose.RoundImage
import com.musica.common.compose.theme.Secondary
import com.musica.dashboard.home.ui.LyricsCardBackgroundColor
import com.musica.dashboard.home.ui.LyricsReaderCardBackgroundColor


@Composable
fun LyricsCard(modifier: Modifier) {
    Box(modifier =  modifier) {
        Column(Modifier.background(
            color = LyricsCardBackgroundColor,
            shape = RoundedCornerShape(26.dp))
        ) {
            Row(
                Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundImage(
                    modifier = Modifier
                        .shadow(
                            shape = RoundedCornerShape(32.dp),
                            elevation = 8.dp,
                            spotColor = Secondary,
                            ambientColor = Secondary
                        ) ,
                    painter = painterResource(id = com.musica.common.R.drawable.share_icon),
                    imageSize = 18.dp,
                    circleSize = 42.dp)

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Lyrics",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }


                RoundImage(
                    modifier = Modifier
                        .shadow(
                            shape = RoundedCornerShape(32.dp),
                            elevation = 8.dp,
                            spotColor = Secondary
                        ),
                    painter = painterResource(id = com.musica.common.R.drawable.share_icon),
                    imageSize = 18.dp,
                    circleSize = 42.dp)
            }

            Column(
                Modifier
                    .background(
                color = LyricsReaderCardBackgroundColor,
                shape = RoundedCornerShape(26.dp))) {
                Text(
                    modifier = Modifier.padding(20.dp),
                    text = "Don’t remind me i’m minding my own damn business don’t try to find me i’m better left alone than in this it doesn’t surprise medo you really think that i could care",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }

}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun LyricsPreview() {
    LyricsCard(modifier = Modifier)
}