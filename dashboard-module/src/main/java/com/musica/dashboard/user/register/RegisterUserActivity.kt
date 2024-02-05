package com.musica.dashboard.user.register

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.musica.common.compose.Exclude
import com.musica.common.compose.KoshaComposeActivity
import com.musica.common.compose.dialog.ProgressDialog
import com.musica.dashboard.user.register.viewmodel.RegisterUserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate

@AndroidEntryPoint
class RegisterUserActivity : KoshaComposeActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    @Exclude
    override fun ActivityContent() {
        val viewModel: RegisterUserViewModel = viewModel()

        val isLoading by viewModel.isLoading.collectAsState()

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var dateOfBirth by remember { mutableStateOf(LocalDate.now()) }
        var dateOfBirthString by remember { mutableStateOf("") }
        var gender by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }
        var otp by remember { mutableStateOf("") }
        var otpFilled by remember { mutableStateOf(false) }

        val navHostController = rememberNavController()
        val scaffoldState = rememberScaffoldState()

        NavHost(navController = navHostController, startDestination = EMAIL_CAPTURE_SCREEN) {
            composable(EMAIL_CAPTURE_SCREEN) {
                EmailCaptureScreen(
                    emailInput = email,
                    scaffoldState = scaffoldState,
                    onBackClick = {
                        finish()
                    },
                    onEmailInputChanged = { emailInput ->
                        email = emailInput
                    },
                    onNextClick = { emailInput ->
                        viewModel.checkEmail(email)
                        email = emailInput
                    }
                )
            }

            composable(OTP_CAPTURE_SCREEN){
                OTPCaptureScreen(
                    scaffoldState = scaffoldState,
                    onBackClick = { navHostController.navigate(EMAIL_CAPTURE_SCREEN) },
                    onOTPInputChanged = { otpInput, otpFilledInput ->
                        otp = otpInput
                        if (otpFilledInput) {
                            viewModel.verifyOtp(otp)
                        }
                    }
                )
            }

            composable(PASSWORD_CAPTURE_SCREEN) {
                PasswordCaptureScreen(
                    password = password,
                    scaffoldState = scaffoldState,
                    onBackClick = { navHostController.navigate(EMAIL_CAPTURE_SCREEN) },
                    onNextClick = { passwordInput ->
                        password = passwordInput
                        navHostController.navigate(DATE_OF_BIRTH_CAPTURE_SCREEN)
                    }
                )
            }

            composable(DATE_OF_BIRTH_CAPTURE_SCREEN) {
                DOBCaptureScreen(
                    dateOfBirth = dateOfBirth,
                    scaffoldState = scaffoldState,
                    onBackClick = { navHostController.navigate(PASSWORD_CAPTURE_SCREEN) },
                    onNextClick = { dateInput ->
                        navHostController.navigate(GENDER_CAPTURE_SCREEN)
                        dateOfBirthString =
                            "${dateInput.dayOfMonth} - ${dateInput.month} - ${dateInput.year}"
                        dateOfBirth = dateInput
                    }
                )
            }

            composable(GENDER_CAPTURE_SCREEN) {
                GenderCaptureScreen(
                    scaffoldState = scaffoldState,
                    selectedGender = gender,
                    onBackClick = { navHostController.navigate(DATE_OF_BIRTH_CAPTURE_SCREEN) },
                    onGenderSelected = { selectedGender ->
                        navHostController.navigate(NAME_CAPTURE_AND_T_AND_CS_SCREEN)
                        gender = selectedGender
                    }
                )
            }

            composable(NAME_CAPTURE_AND_T_AND_CS_SCREEN) {
                NameAnsTsAndCsCaptureScreen(
                    name = name,
                    scaffoldState = scaffoldState,
                    onBackClick = { navHostController.navigate(GENDER_CAPTURE_SCREEN) },
                    onCreateAccountClick = { nameInput ->
                        name = nameInput
                        viewModel.registerUser(
                            name = name,
                            gender = gender,
                            dateOfBirth = dateOfBirthString,
                            password = password,
                            email = email,
                        )
                    }
                )
            }
        }

        if (isLoading) {
            ProgressDialog()
        }

        LaunchedEffect(viewModel) {
            launch {
                viewModel.errorMessage.collectLatest {
                    scaffoldState.snackbarHostState.showSnackbar(it)
                }
            }

            launch {
                viewModel.emailCheckSuccessNav.collectLatest {
                    navHostController.navigate(it)
                }
            }

            launch {
                viewModel.resultRegisterSuccessIntent.collectLatest {
                    startActivity(it)
                }
            }

            launch {
                viewModel.verifyOtpSuccessNav.collectLatest {
                    navHostController.navigate(it)
                }
            }

        }
    }

    companion object {
        const val EMAIL_CAPTURE_SCREEN = "email-capture"
        const val PASSWORD_CAPTURE_SCREEN = "password-capture"
        const val DATE_OF_BIRTH_CAPTURE_SCREEN = "date-of-birth-capture"
        const val GENDER_CAPTURE_SCREEN = "gender-capture"
        const val NAME_CAPTURE_AND_T_AND_CS_SCREEN = "name-and-terms"
        const val OTP_CAPTURE_SCREEN = "otp-capture"
    }
}