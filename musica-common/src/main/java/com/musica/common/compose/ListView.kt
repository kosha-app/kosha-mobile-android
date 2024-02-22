package com.musica.common.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.musica.common.R
import com.musica.common.compose.theme.KoshaTheme
import com.musica.common.compose.theme.MusicaBlueColor
import com.musica.common.compose.theme.Primary
import com.musica.common.compose.theme.Secondary
import com.musica.common.compose.theme.White

@OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class)
@Composable
@Exclude
fun TrackItem(
    coverUrl: String,
    trackName: String,
    trackArtist: String,
    onTrackClick: () -> Unit,
    trailing: @Composable  (() -> Unit)? = null
) {

    ListItem(
        modifier = Modifier
            .clickable(onClick = onTrackClick),
        icon = {
            GlideImage(
                modifier = Modifier
                    .size(40.dp)
                    .background(Primary, shape = RoundedCornerShape(10.dp)),
                model = coverUrl ,
                contentDescription = "Cover Image"
            )
        },
        text = {
            Text(
                text = trackName,
                color = White
            )
        },
        secondaryText = {
            Text(
                text = trackArtist,
                color = Secondary
            )
        },
        trailing = trailing
    )
}