package com.musica.dashboard.user.signin

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
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musica.common.compose.Exclude
import com.musica.common.compose.button.PrimaryButton
import com.musica.common.compose.dialog.ProgressDialog
import com.musica.common.compose.input.InputText
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.compose.theme.MusicaBlueColor
import com.musica.common.compose.theme.Negative
import com.musica.common.compose.theme.Secondary

@Composable
@Exclude
fun SignInScreen(
    scaffoldState: ScaffoldState,
    isLoading: Boolean,
    onSignIbButtonClick: (username: String, password: String) -> Unit
) {

    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }

    var showPasswordText by remember { mutableStateOf(false) }

    Secondary


    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { hostState ->
            SnackbarHost(hostState = hostState) { snackBarData ->
                Snackbar(snackbarData = snackBarData, backgroundColor = Negative)
            }
        }
    ) { padding ->

        if (isLoading) {
            ProgressDialog()
        }
        Column(
            modifier = Modifier
                .padding(padding)
                .background(brush = Brush.verticalGradient(BackgroundGradientColors))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                modifier = Modifier.padding(vertical = 16.dp),
                text = "KOSHA",
                color = Secondary,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )

            Text(
                modifier = Modifier.padding(bottom = 60.dp),
                text = "Sign in your profile",
                color = Secondary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            InputText(
                modifier = Modifier.padding(bottom = 24.dp),
                value = emailInput.trim(),
                onValueChange = { emailInput = it },
                placeholder = "Username"
            )

            InputText(modifier = Modifier.padding(bottom = 24.dp),
                value = passwordInput.trim(),
                onValueChange = { passwordInput = it },
                placeholder = "Password",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
                visualTransformation = if (showPasswordText) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Text(
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

            PrimaryButton(modifier = Modifier.padding(),
                buttonText = "SIGN IN",
                buttonColor = MusicaBlueColor,
                width = 180.dp,
                onClick = { onSignIbButtonClick(emailInput, passwordInput) })
        }

    }
}