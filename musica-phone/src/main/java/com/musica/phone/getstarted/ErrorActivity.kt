package com.musica.phone.getstarted

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.musica.common.compose.KoshaComposeActivity
import com.musica.common.compose.button.PrimaryButton
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.compose.theme.MusicaBlueColor

class ErrorActivity : KoshaComposeActivity() {
    @Composable
    override fun ActivityContent() {
        ErrorScreen(onRetryClick = { startActivity(Intent(this, GetStartedActivity::class.java)) })
    }
}

@Composable
fun ErrorScreen(
    onRetryClick: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(BackgroundGradientColors)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        PrimaryButton(
            modifier = Modifier,
            buttonText = "Try Again",
            buttonColor = MusicaBlueColor,
            width = 188.dp,
            onClick = onRetryClick
        )
    }
}