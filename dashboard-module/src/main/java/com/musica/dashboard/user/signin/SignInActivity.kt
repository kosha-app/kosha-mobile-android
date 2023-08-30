package com.musica.dashboard.user.signin

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.musica.common.compose.MusicaComposeActivity
import com.musica.dashboard.user.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInActivity: MusicaComposeActivity() {
    @Composable
    override fun ActivityContent() {
        val viewModel: SignInViewModel = viewModel()

        val scaffoldState = rememberScaffoldState()
        val isLoading by viewModel.isLoading.collectAsState()

        SignInScreen(
            scaffoldState = scaffoldState,
            isLoading = isLoading,
        ){ username, password ->
            viewModel.userSignIn(username, password)
        }

        LaunchedEffect(viewModel){

            launch {
                viewModel.isSuccessful.collectLatest { startActivity(it) }
            }

            launch {
                viewModel.errorMessage.collectLatest { scaffoldState.snackbarHostState.showSnackbar(it) }
            }

        }
    }
}