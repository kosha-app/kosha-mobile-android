package com.musica.dashboard.user.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musica.common.compose.TopBar
import com.musica.common.compose.button.PrimaryButton
import com.musica.common.compose.input.InputText
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.compose.theme.KoshaTheme
import com.musica.common.compose.theme.MusicaBlueColor
import com.musica.common.compose.theme.Negative
import com.musica.common.compose.theme.Secondary

@Composable
fun PasswordCaptureScreen(
    password: String,
    scaffoldState: ScaffoldState,
    onBackClick: () -> Unit,
    onNextClick: (password: String) -> Unit
) {
    var showPasswordText by remember { mutableStateOf(false) }

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

            var emailInput by remember { mutableStateOf(password) }

            Text(
                modifier = Modifier.padding(top = 48.dp, bottom = 8.dp),
                text = "Create a password",
                color = Secondary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            InputText(
                value = emailInput.trim(),
                placeholder = "Password",
                onValueChange = {
                    emailInput = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
                visualTransformation = if (showPasswordText) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    androidx.compose.material3.Text(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable {
                                showPasswordText = !showPasswordText
                            },
                        text = if (showPasswordText) "HIDE" else "SHOW",
                        color = Color.LightGray,
                        fontSize = 16.sp,
                    )
                }
            )
            Text(
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                text = "Your password must at least be 8 characters long",
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
@Preview
private fun PasswordCaptureScreenPreview(){
    KoshaTheme {
        PasswordCaptureScreen(
            password = "Bello",
            scaffoldState = rememberScaffoldState(),
            onBackClick = { /*TODO*/ },
            onNextClick = {})
    }
}