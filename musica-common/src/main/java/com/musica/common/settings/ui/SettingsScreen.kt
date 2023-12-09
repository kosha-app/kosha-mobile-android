package com.musica.common.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musica.common.R
import com.musica.common.compose.RoundImage
import com.musica.common.compose.TopBar
import com.musica.common.compose.button.PrimaryButton
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.compose.theme.GreysColorMix_2_Reverse
import com.musica.common.compose.theme.KoshaTheme
import com.musica.common.compose.theme.MusicaBlueColor
import com.musica.common.compose.theme.Negative
import com.musica.common.compose.theme.Primary
import com.musica.common.compose.theme.ProfileLetterBackGroundColors
import com.musica.common.compose.theme.Secondary

@Composable
fun SettingsScreen(
    name: String,
    scaffoldState: ScaffoldState,
    logOutItems: List<SettingsItemsOptions>,
    onBackPressed: () -> Unit
) {
    val scrollState = rememberScrollState()
    Scaffold(topBar = { TopBar(title = "Settings", onBackPressed = onBackPressed) },
        scaffoldState = scaffoldState,
        snackbarHost = { hostState ->
            SnackbarHost(hostState = hostState) { snackBarData ->
                Snackbar(snackbarData = snackBarData, backgroundColor = Negative)
            }
        }
    ) { paddingValues ->
        Column(
            Modifier
                .background(brush = Brush.verticalGradient(BackgroundGradientColors))
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileClickView(
                    name = name,
                    modifier = Modifier.shadow(
                        elevation = 5.dp, shape = RoundedCornerShape(26.dp)
                    )
                )

                PrimaryButton(modifier = Modifier.padding(top = 30.dp),
                    buttonText = "Go Premium",
                    buttonColor = MusicaBlueColor,
                    width = 136.dp,
                    onClick = {
                        //TODO to implement go premium
                    }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 6.dp, shape = RoundedCornerShape(26.dp))
                        .background(
                            color = Primary, shape = RoundedCornerShape(26.dp)
                        )
                        .padding(vertical = 30.dp, horizontal = 20.dp)
                ) {
                    SettingsOptions(
                        logOutItems
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsOptions(
    logOutItems: List<SettingsItemsOptions>
) {
    Column {
//        LazyColumn(Modifier.fillMaxWidth()) {
//            items(items = settingsItems(logOutItems), itemContent = { item ->
//                SettingsHeader(heading = item.heading)
//                Spacer(modifier = Modifier.padding(vertical = 4.dp))
//                item.content.invoke()
//            })
//        }

        settingsItems(logOutItems).forEach {
            SettingsHeader(heading = it.heading)
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            it.content.invoke()
        }
    }
}

@Composable
fun SettingsItemView(
    header: String,
    description: String,
    onOptionClick: () -> Unit,
    settingsTrailing: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clickable(onClick = onOptionClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = header,
                color = Color.White,
                fontSize = 14.sp
            )
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = description,
                color = Secondary,
                fontSize = 11.sp
            )
        }
        settingsTrailing.invoke()
    }
}


@Composable
fun ProfileClickView(
    modifier: Modifier = Modifier,
    name: String
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(GreysColorMix_2_Reverse),
                    RoundedCornerShape(26.dp)
                )
                .padding(20.dp), verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.verticalGradient(ProfileLetterBackGroundColors)
                    ), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "K", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold
                )
            }

            Column(
                Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = name, color = Color.White, fontSize = 18.sp
                )
                Row {
                    Text(
                        text = "0",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier.padding(start = 2.dp),
                        text = "Followers |",
                        color = Secondary,
                        fontSize = 14.sp
                    )
                    Text(
                        modifier = Modifier.padding(start = 2.dp),
                        text = "0",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier.padding(start = 2.dp),
                        text = "Following",
                        color = Secondary,
                        fontSize = 14.sp
                    )
                }
            }

            RoundImage(
                modifier = Modifier.shadow(
                    elevation = 10.dp, shape = CircleShape
                ),
                painter = painterResource(id = R.drawable.arrow_right),
                imageSize = 20.dp,
                circleSize = 44.dp
            )
        }
    }
}

@Composable
@Preview
fun SettingsScreenPreview() {
    KoshaTheme {
        SettingsScreen(
            name = "Sageem",
            scaffoldState = rememberScaffoldState(),
            onBackPressed = {},
            logOutItems = listOf(
                SettingsItemsOptions("Logout", "This will log you out", {})
            )
        )
    }
}