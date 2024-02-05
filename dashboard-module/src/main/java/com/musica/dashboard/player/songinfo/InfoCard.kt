package com.musica.dashboard.player.songinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musica.common.R
import com.musica.common.compose.Exclude
import com.musica.common.compose.theme.Primary
import com.musica.common.compose.theme.Secondary

@Composable
@Exclude
fun InfoCard(
    modifier: Modifier,
    headingText: String,
    image: Painter = painterResource(id = R.drawable.premium_nav_icon),
    descriptionHeader: String,
    descriptionSubText: String,
    description: String? = null,
    buttonText: String
) {
    Box(modifier = modifier) {
        Column(
            Modifier.background(
                color = Primary, shape = RoundedCornerShape(26.dp)
            )
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.padding(bottom = 20.dp),
                        text = headingText,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }

                Image(
                    modifier = Modifier
                        .width(288.dp)
                        .height(220.dp)
                        .shadow(
                            elevation = 50.dp,
                            ambientColor = Color.White,
                            shape = RoundedCornerShape(26.dp)
                        )
                        .background(
                            color = Color.Gray, shape = RoundedCornerShape(
                                32.dp
                            )
                        ),
                    painter = painterResource(id = R.drawable.flash),
                    contentDescription = "Song Cover"
                )

                Row(
                    Modifier.padding(top = 20.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        Modifier.weight(1f)
                    ) {
                        Text(
                            text = descriptionHeader, fontSize = 16.sp, color = Color.White
                        )
                        Text(
                            modifier = Modifier.padding(),
                            text = descriptionSubText,
                            fontSize = 12.sp,
                            color = Secondary
                        )
                    }
                    Button(
                        modifier = Modifier.shadow(
                            elevation = 3.dp, spotColor = Secondary, shape = RoundedCornerShape(
                                32.dp
                            )
                        ),
                        onClick = { /* Handle button click here */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) {
                        Text(
                            text = buttonText, color = Color.White
                        )
                    }

                }

                if (description != null) {
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = description,
                        fontSize = 12.sp,
                        color = Secondary,
                    )
                }


            }

        }
    }

}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
@Exclude
fun LyricsPreview() {
    InfoCard(
        modifier = Modifier,
        headingText = "About The Artist",
        descriptionHeader = "SagEM",
        descriptionSubText = "23,545,456 monthly listeners",
        buttonText = "Follow",
        description = "An internet based vocalist, producer, writer, director and performance artist, Oliver Tree..."
    )
}