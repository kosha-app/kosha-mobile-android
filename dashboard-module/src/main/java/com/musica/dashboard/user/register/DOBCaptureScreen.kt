package com.musica.dashboard.user.register

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.commandiron.wheel_picker_compose.WheelDatePicker
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import com.musica.common.compose.TopBar
import com.musica.common.compose.button.PrimaryButton
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.compose.theme.MusicaBlueColor
import com.musica.common.compose.theme.MusicaphoneTheme
import com.musica.common.compose.theme.Negative
import com.musica.common.compose.theme.Secondary
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DOBCaptureScreen(
    dateOfBirth: LocalDate,
    scaffoldState: ScaffoldState,
    onBackClick: () -> Unit,
    onNextClick: (date: LocalDate) -> Unit
) {

    var chosenDate by remember { mutableStateOf(dateOfBirth) }

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
                modifier = Modifier.padding(top = 48.dp, bottom = 24.dp),
                text = "What's your date of birth?",
                color = Secondary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            WheelDatePicker(
                startDate = dateOfBirth,
                textColor = Color.White,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    color = MusicaBlueColor.copy(alpha = 0.5f),
                    border = BorderStroke(2.dp, MusicaBlueColor)
                )
            ) { snappedDate ->
                chosenDate = snappedDate
            }

            PrimaryButton(
                modifier = Modifier.padding(top = 32.dp),
                buttonText = "Next",
                buttonColor = MusicaBlueColor,
                width = 100.dp,
                onClick = {
                    onNextClick(chosenDate)
                }
            )

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
private fun DOBPreview(){
    MusicaphoneTheme {
        DOBCaptureScreen(
            dateOfBirth = LocalDate.now(),
            scaffoldState = rememberScaffoldState(),
            onBackClick = { /*TODO*/ },
            onNextClick = {}
        )
    }

}