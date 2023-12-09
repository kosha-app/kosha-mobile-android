package com.musica.phone.getstarted

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musica.common.R
import com.musica.common.compose.button.PrimaryButton
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.compose.theme.KoshaTheme
import com.musica.common.compose.theme.MusicaBlueColor
import com.musica.common.compose.theme.Secondary

@Composable
fun LandingScreen(
    onSignInClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Scaffold {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(BackgroundGradientColors)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {

            Image(
                modifier = Modifier
                    .padding(bottom = 75.dp)
                    .size(75.dp),
                painter = painterResource(id = R.mipmap.ic_launcher_kosha_foreground),
                contentDescription = "Logo with slogan"
            )

            Text(
                modifier = Modifier.padding(bottom = 48.dp),
                text = "Play, Discover, Connect",
                color = Secondary,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            PrimaryButton(
                buttonText = "Sign up free",
                buttonColor = MusicaBlueColor,
                width = 288.dp,
                onClick = onRegisterClick
            )

            PrimaryButton(
                buttonText = "Log in",
                buttonColor = MusicaBlueColor,
                width = 288.dp,
                onClick = onSignInClick
            )
        }
    }
}

@Composable
@Preview
fun LandingScreenPreview(){
    KoshaTheme {
        LandingScreen(onSignInClick = { }, onRegisterClick = {})
    }
}