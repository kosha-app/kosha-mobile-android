package com.musica.dashboard.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musica.common.R
import com.musica.common.compose.Exclude
import com.musica.common.compose.RoundImage
import com.musica.dashboard.R.string

@Composable
@Exclude
fun DashboardTopBar(
    omSettingsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(color = Color(0xFF353A40))
            .fillMaxWidth()
            .graphicsLayer {

            }
            .padding(vertical = 28.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = string.dashboard_top_bar_header_text),
            fontSize = 18.sp,
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
//        RoundImage(
//            modifier = Modifier
//                .shadow(
//                    elevation = 8.dp,
//                    spotColor = Color.Black,
//                    ambientColor = Color.Black,
//                    shape = RoundedCornerShape(32.dp)
//                ),
//            circleSize = 44.dp,
//            painter = painterResource(id = R.drawable.flash),
//            imageSize = 24.dp,
//        )
//        RoundImage(
//            modifier = Modifier
//                .padding(start = 26.dp)
//                .shadow(
//                    elevation = 8.dp,
//                    spotColor = Color.Black,
//                    ambientColor = Color.Black,
//                    shape = RoundedCornerShape(32.dp)
//                ),
//            circleSize = 44.dp,
//            painter = painterResource(id = R.drawable.catagory_icon),
//            imageSize = 24.dp,
//        )

        RoundImage(
            modifier = Modifier
                .padding(start = 26.dp)
                .shadow(
                    elevation = 8.dp,
                    spotColor = Color.Black,
                    ambientColor = Color.Black,
                    shape = RoundedCornerShape(32.dp)
                ),
            circleSize = 44.dp,
            painter = painterResource(id = R.drawable.settings_icon),
            imageSize = 24.dp,
            onClick = omSettingsClick
        )

    }
}