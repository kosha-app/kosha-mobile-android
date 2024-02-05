package com.musica.phone.getstarted

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.musica.common.compose.Exclude
import com.musica.common.compose.KoshaComposeActivity
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.compose.theme.MusicaBlueColor
import com.musica.common.compose.theme.Secondary
import com.musica.phone.getstarted.viewmodel.GetStartedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GetStartedActivity : KoshaComposeActivity() {

    @Composable
    @Exclude
    override fun ActivityContent() {
        val viewModel: GetStartedViewModel = viewModel()

        val scaffoldState = rememberScaffoldState()

        val isLoading by viewModel.isLoading.collectAsState()


        GetStartedScreen(
            scaffoldState,
            isLoading
        )

        LaunchedEffect(viewModel ){
            launch{
                viewModel.errorMessage.collectLatest {
                    scaffoldState.snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Long)
                }
            }

            launch {
                viewModel.returnIntent.collectLatest {
                    startActivity(it)
                }
            }
        }
    }
}

@Composable
@Exclude
fun GetStartedScreen(
    scaffoldState: ScaffoldState,
    isLoading: Boolean
) {
    Scaffold(
        scaffoldState = scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(brush = Brush.verticalGradient(BackgroundGradientColors)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                modifier = Modifier.padding(bottom = 50.dp),
                text = "KOSHA",
                color = Secondary,
                fontWeight = FontWeight.Bold,
                fontSize = 60.sp
            )
            if (isLoading){
                CircularProgressIndicator(modifier = Modifier, color = MusicaBlueColor)
            }
        }
    }
}