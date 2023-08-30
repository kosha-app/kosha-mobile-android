package com.musica.dashboard.user.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.musica.common.compose.InputText
import com.musica.common.compose.button.MusicButton
import com.musica.common.compose.dialog.ProgressDialog
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.compose.theme.MusicaBlueColor
import com.musica.common.compose.theme.MusicaphoneTheme
import com.musica.common.compose.theme.Primary
import com.musica.common.compose.theme.Secondary
import com.musica.common.compose.theme.Tertiary

@Composable
fun SignInScreen(
    scaffoldState: ScaffoldState,
    isLoading: Boolean,
    onSignIbButtonClick: (username: String, password: String) -> Unit
) {

    var usernameInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }

    var showPasswordText by remember { mutableStateOf(false) }

    Secondary


    Scaffold(
        scaffoldState = scaffoldState
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
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                modifier = Modifier.padding(bottom = 16.dp),
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
                value = usernameInput,
                onValueChange = { usernameInput = it },
                placeholder = "Username"
            )

            InputText(modifier = Modifier.padding(bottom = 24.dp),
                value = passwordInput,
                onValueChange = { passwordInput = it },
                placeholder = "Password",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
                })

            MusicButton(modifier = Modifier.padding(),
                buttonText = "SIGN IN",
                buttonColor = MusicaBlueColor,
                width = 180.dp,
                onClick = { onSignIbButtonClick(usernameInput, passwordInput) })
        }

    }
}