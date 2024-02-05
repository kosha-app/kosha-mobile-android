package com.musica.dashboard.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.musica.common.compose.Exclude
import com.musica.common.compose.theme.Primary
import com.musica.common.compose.theme.Secondary


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
@Exclude
fun DashboardItem(
    modifier: Modifier, imageUrl: String, header: String? = null, description: String, onItemClick: () -> Unit
) {
    Column(modifier = modifier) {
        Column(
            Modifier
                .shadow(
                    20.dp,
                    shape = RoundedCornerShape(26.dp)
                )
                .width(130.dp)
                .height(214.dp)
                .background(
                    color = Primary, shape = RoundedCornerShape(26.dp)
                )
                .clickable(onClick = onItemClick)
        ) {
            GlideImage(
                modifier = Modifier
                    .width(131.55209.dp)
                    .height(131.55209.dp),
                model = imageUrl,
                contentDescription = "Pic Nje"
            )
            if (header != null) {
                Text(
                    modifier = Modifier.padding(top = 12.dp, start = 10.dp, end = 10.dp),
                    text = header,
                    fontSize = 14.sp,
                    color = Color(0xFFE06D94),
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp, start = 10.dp, end = 10.dp),
                text = description,
                lineHeight = 14.sp,
                fontSize = 11.sp,
                color = Secondary
            )
        }
    }

}