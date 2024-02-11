package com.musica.dashboard.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.musica.common.compose.Exclude
import com.musica.common.compose.theme.Secondary
import com.musica.dashboard.home.ui.RecentPlayedBackground

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
@Exclude
fun PopularArtistCard(
    modifier: Modifier,
    cardImageUrl: String,
    artistName: String,
    onClick: () -> Unit
) {
    Column(modifier = modifier.clickable(onClick = onClick)) {
        Column(modifier = Modifier
            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(26.dp)
            )
            .width(110.dp)
            .height(130.dp)
            .background(
                color = RecentPlayedBackground,
                shape = RoundedCornerShape(26.dp)
            )
            .padding(horizontal = 17.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                modifier = Modifier
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .size(76.dp),
                model = cardImageUrl,
                contentDescription = "Artist Card Image"
            )
            Text(
                modifier = Modifier
                    .padding(top = 12.dp),
                text = artistName,
                fontSize = 11.sp,
                color = Secondary
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
@Exclude
fun Preview(){
    PopularArtistCard(modifier = Modifier.padding(start = 16.dp, top = 20.dp, bottom = 40.dp),cardImageUrl = "", artistName = "NAzo Mix", {})
}