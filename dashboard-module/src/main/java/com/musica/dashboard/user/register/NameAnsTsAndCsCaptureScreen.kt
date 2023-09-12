package com.musica.dashboard.user.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musica.common.compose.input.InputText
import com.musica.common.compose.TopBar
import com.musica.common.compose.button.MusicButton
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.compose.theme.MusicaBlueColor
import com.musica.common.compose.theme.MusicaphoneTheme
import com.musica.common.compose.theme.Negative
import com.musica.common.compose.theme.Secondary

@Composable
fun NameAnsTsAndCsCaptureScreen(
    name: String,
    scaffoldState: ScaffoldState,
    onBackClick: () -> Unit,
    onCreateAccountClick: (name: String) -> Unit
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

            var nameInput by remember { mutableStateOf(name) }

            Column(
                Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(top = 48.dp, bottom = 8.dp),
                    text = "What's your name?",
                    color = Secondary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                InputText(
                    value = nameInput.trim(),
                    placeholder = "Name",
                    onValueChange = {
                        nameInput = it
                    }
                )
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp, bottom = 16.dp),
                    text = "This will appear in your Kosha profile.",
                    color = Secondary
                )

                //TODO to implement terms and conditions
            }


            MusicButton(
                modifier = Modifier.padding(bottom = 40.dp),
                buttonText = "Create Account",
                buttonColor = MusicaBlueColor,
                width = 150.dp,
                onClick = {
                    onCreateAccountClick(nameInput)
                }
            )

        }
    }
}

@Composable
@Preview
private fun NameAnsTsAndCsCapturePreview() {
    MusicaphoneTheme {
        NameAnsTsAndCsCaptureScreen(
            name = "kjrhlwj",
            scaffoldState = rememberScaffoldState(),
            onBackClick = { /*TODO*/ },
            onCreateAccountClick = {}
        )
    }
}