package com.musica.dashboard.user.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musica.common.compose.InputText
import com.musica.common.compose.theme.MusicaphoneTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen() {

    Scaffold { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(brush = Brush.verticalGradient()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    modifier = Modifier.padding(bottom = 50.dp),
                    text = "Create your profile",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                InputText(
                    value = "",
                    onValueChange = {},
                    placeholder = "Name"
                )

                InputText(
                    value = "",
                    onValueChange = {},
                    placeholder = "Surname"
                )

                InputText(
                    value = "",
                    onValueChange = {},
                    placeholder = "Username"
                )

                InputText(
                    value = "",
                    onValueChange = {},
                    placeholder = "Email"
                )

                InputText(
                    value = "",
                    onValueChange = {},
                    placeholder = "Cell Number"
                )

                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Register")
                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun Previewhy() {
    MusicaphoneTheme {
        RegisterScreen()
    }
}