package com.musica.common.settings

import android.content.Intent
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.musica.common.compose.MusicaComposeActivity
import com.musica.common.compose.dialog.ProgressDialog
import com.musica.common.settings.ui.SettingsItemsOptions
import com.musica.common.settings.ui.SettingsScreen
import com.musica.common.settings.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsActivity : MusicaComposeActivity() {
    @Composable
    override fun ActivityContent() {
        val viewModel: SettingsViewModel = viewModel()

        val isLoading by viewModel.isLoading.collectAsState()
        val name by viewModel.name.collectAsState()

        val scaffoldState = rememberScaffoldState()

        val logOutItems = listOf(
            SettingsItemsOptions(
                heading = "Log Out",
                description = "You are logged in as $name",
                onClick = { viewModel.logDeviceOut() }
            )
        )

        SettingsScreen(
            name = name,
            scaffoldState = scaffoldState,
            logOutItems = logOutItems,
            onBackPressed = { finish() }
        )

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
                viewModel.logoutSuccessEvent.collectLatest {
                    val packageName = applicationContext.packageName
                    val intent = packageManager.getLaunchIntentForPackage(packageName)
                    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

                    if (intent != null) {
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

}