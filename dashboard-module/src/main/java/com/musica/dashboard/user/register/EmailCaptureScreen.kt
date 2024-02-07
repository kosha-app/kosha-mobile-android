package com.musica.dashboard.user.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musica.common.compose.Exclude
import com.musica.common.compose.TopBar
import com.musica.common.compose.button.PrimaryButton
import com.musica.common.compose.input.InputText
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.compose.theme.KoshaTheme
import com.musica.common.compose.theme.MusicaBlueColor
import com.musica.common.compose.theme.Negative
import com.musica.common.compose.theme.Secondary

@Composable
@Exclude
fun EmailCaptureScreen(
    emailInput: String,
    scaffoldState: ScaffoldState,
    onBackClick: () -> Unit,
    onEmailInputChanged: (email: String) -> Unit,
    onNextClick: (email: String) -> Unit
) {


    Scaffold(
        topBar = {
            TopBar(
                title = "Create Account",
                onBackPressed = onBackClick
            )
        },
        snackbarHost = { hostState ->
            SnackbarHost(hostState = hostState) { snackBarData ->
                Snackbar(snackbarData = snackBarData, backgroundColor = Negative)
            }
        },
        scaffoldState = scaffoldState
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(BackgroundGradientColors)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                modifier = Modifier.padding(top = 48.dp, bottom = 8.dp),
                text = "What's your email?",
                color = Secondary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            InputText(
                modifier = Modifier.padding(horizontal = 24.dp),
                value = emailInput.trim(),
                placeholder = "Email",
                onValueChange = {
                    onEmailInputChanged(it)
                }
            )
            Text(
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                text = "You will need to confirm this email",
                color = Secondary
            )

            PrimaryButton(
                buttonText = "Next",
                buttonColor = MusicaBlueColor,
                width = 100.dp,
                onClick = {
                    onNextClick(emailInput)
                }
            )

        }
    }
}

@Composable
@Exclude
@Preview
private fun EmailCapturePreview() {
    KoshaTheme {
        EmailCaptureScreen(
            emailInput = "email",
            onEmailInputChanged = {},
            scaffoldState = rememberScaffoldState(),
            onBackClick = { /*TODO*/ },
            onNextClick = {})
    }
}