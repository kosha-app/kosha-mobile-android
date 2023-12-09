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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musica.common.compose.TopBar
import com.musica.common.compose.input.OtpInputField
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.compose.theme.KoshaTheme
import com.musica.common.compose.theme.Negative
import com.musica.common.compose.theme.Secondary
import kotlinx.coroutines.android.awaitFrame

@Composable
fun OTPCaptureScreen(
    scaffoldState: ScaffoldState,
    onBackClick: () -> Unit,
    onOTPInputChanged: (otp: String, otpFilled: Boolean) -> Unit
) {

    var otpInput by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

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
                text = "Confirm OTP",
                color = Secondary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                text = "Check your email for the otp",
                color = Secondary
            )

            OtpInputField(
                modifier = Modifier.focusRequester(focusRequester),
                otpText = otpInput,
                onOtpTextChange = { otp, otpFilled ->
                    otpInput = otp
                    onOTPInputChanged(otp, otpFilled)
                }
            )
        }
    }

    LaunchedEffect(focusRequester) {
        awaitFrame()
        focusRequester.requestFocus()
    }
}

@Preview
@Composable
private fun OTPPreview() {
    KoshaTheme {
        OTPCaptureScreen(
            scaffoldState = rememberScaffoldState(),
            onBackClick = { /*TODO*/ },
            onOTPInputChanged = { _, _ -> }
        )
    }
}