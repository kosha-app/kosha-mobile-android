package com.musica.common.compose.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.musica.common.compose.Exclude
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.compose.theme.KoshaTheme
import com.musica.common.compose.theme.MusicaBlueColor
import com.musica.common.compose.theme.Secondary
import com.musica.common.compose.theme.Tertiary

@Composable
@Exclude
fun ProgressDialog(
    title: String? = null,
    description: String? = null,
) {
    Dialog(
        onDismissRequest = { /* Non-dismissible. */ },
    ) {
        Surface {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .shadow(36.dp)
                    .background(
                        color = Tertiary
                    )
                    .padding(32.dp)
            ) {
                CircularProgressIndicator(
                    color = MusicaBlueColor,
                    modifier = Modifier.testTag("progressIndicator")
                )
                title?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Secondary
                    )
                }
                description?.let {
                    androidx.compose.material.Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        color = Secondary
                    )
                }
            }
        }
    }
}

@Composable
@Exclude
fun ProgressScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(BackgroundGradientColors))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = MusicaBlueColor,
            modifier = Modifier.testTag("progressIndicator")
        )
    }
}

@Composable
@Exclude
@Preview()
fun PreviewDialog() {
    KoshaTheme {
        ProgressDialog(description = "nazo zeyeza mchana")
    }
}

@Composable
@Exclude
@Preview(showSystemUi = true)
fun PreviewScreen() {
    KoshaTheme {
        ProgressScreen()
    }
}