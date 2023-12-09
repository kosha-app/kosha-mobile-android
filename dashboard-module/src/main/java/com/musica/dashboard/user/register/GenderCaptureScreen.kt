package com.musica.dashboard.user.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.musica.common.compose.TopBar
import com.musica.common.compose.chip.KoshaChip
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.compose.theme.KoshaTheme
import com.musica.common.compose.theme.Negative
import com.musica.common.compose.theme.Secondary

@Composable
fun GenderCaptureScreen(
    scaffoldState: ScaffoldState,
    selectedGender: String,
    onBackClick: () -> Unit,
    onGenderSelected: (gender: String) -> Unit
) {

    val genders = listOf("Male", "Female", "Non-binary", "Other")

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
                text = "What's your gender?",
                color = Secondary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (gender in genders) {
                    KoshaChip(
                        text = gender,
                        isSelected = selectedGender == gender,
                        onGenderSelected = onGenderSelected
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                }
            }

        }
    }
}

@Composable
@Preview
fun GenderCapturePreview() {
    KoshaTheme {
        GenderCaptureScreen(
            rememberScaffoldState(),
            "Male",
            {},
            {}
        )
    }
}