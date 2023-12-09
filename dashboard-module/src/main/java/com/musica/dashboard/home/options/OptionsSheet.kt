package com.musica.dashboard.home.options

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.musica.common.R
import com.musica.common.compose.RoundImage
import com.musica.common.compose.theme.DarkGrey
import com.musica.common.compose.theme.GreysColorMix_2
import com.musica.common.compose.theme.KoshaTheme
import com.musica.common.compose.theme.MusicaBlueColor
import com.musica.common.compose.theme.Primary
import com.musica.common.compose.theme.Secondary

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun OptionsSheet(
    coverUrl: String, trackName: String, trackArtist: String
) {
    Column(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(GreysColorMix_2),
                shape = RoundedCornerShape(topEnd = 26.dp, topStart = 26.dp)
            )
            .height(714.dp)
            .shadow(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            GlideImage(
                model = coverUrl,
                contentDescription = stringResource(id = R.string.music_nav_bar_navigation_icon_description_text),
                modifier = Modifier
                    .shadow(
                        elevation = 5.dp, shape = RoundedCornerShape(28.dp)
                    )
                    .height(42.dp)
                    .width(42.dp)
                    .background(
                        color = Primary, shape = RoundedCornerShape(28.dp)
                    )
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = trackName, color = Color.White, fontSize = 12.sp
                )
                Text(
                    text = trackArtist, color = Secondary, fontSize = 12.sp
                )
            }

            RoundImage(
                modifier = Modifier.shadow(elevation = 24.dp, shape = RoundedCornerShape(32.dp)),
                painter = painterResource(id = R.drawable.arrow_down_icon),
                imageSize = 20.dp,
                circleSize = 44.dp
            )
        }

        LazyColumn(Modifier.fillMaxWidth()) {
            items(items = OPTIONS_ITEMS, itemContent = { item ->
                OptionsItem(icon = item.icon,
                    itemClick = { /*TODO*/ },
                    mainText = item.mainText,
                    trailingText = item.trailingText
                )
            })
        }

    }
}

@Composable
fun OptionsItem(
    @DrawableRes icon: Int, itemClick: () -> Unit, mainText: String, trailingText: String? = null
) {
    Row(
        modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        RoundImage(
            modifier = Modifier
                .shadow(elevation = 24.dp, shape = RoundedCornerShape(32.dp))
                .padding(end = 12.dp),
            painter = painterResource(id = icon),
            imageSize = 20.dp,
            circleSize = 44.dp,
            onClick = itemClick
        )
        Row(
            modifier = Modifier
                .shadow(elevation = 24.dp, shape = RoundedCornerShape(26.dp))
                .background(color = DarkGrey, shape = RoundedCornerShape(26.dp))
                .padding(14.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = mainText,
                fontSize = 14.sp,
                color = Color.White
            )

            Image(
                modifier = Modifier.size(12.dp),
                painter = painterResource(id = R.drawable.premium_nav_icon),
                contentDescription = "Premium Nav Icon",
                colorFilter = ColorFilter.tint(MusicaBlueColor)
            )
            if (trailingText != null) {
                Text(
                    text = trailingText, fontSize = 12.sp, color = MusicaBlueColor
                )
            }

        }
    }
}

val OPTIONS_ITEMS = listOf(
    Option(icon =  R.drawable.premium_nav_icon, "Listen to music ad-free", "Premium"),
    Option(icon =  R.drawable.premium_nav_icon, "Like"),
    Option(icon =  R.drawable.premium_nav_icon, "Hide this song"),
    Option(icon =  R.drawable.premium_nav_icon, "Add to playlist"),
    Option(icon =  R.drawable.premium_nav_icon, "Add to queue"),
    Option(icon =  R.drawable.premium_nav_icon, "View album"),
    Option(icon =  R.drawable.premium_nav_icon, "View artist"),
    Option(icon =  R.drawable.share_icon, "Share"),
    Option(icon =  R.drawable.premium_nav_icon, "Show credits"),
    Option(icon =  R.drawable.premium_nav_icon, "Show spotify code"),


)

class Option(@DrawableRes val icon: Int, val mainText: String, val trailingText: String? = null)

@Preview(showSystemUi = true)
@Composable
private fun OptionsUIPreview() {
    KoshaTheme {
        OptionsSheet("", "Gazzet (Kazet)", trackArtist = "SageEM")

    }
}