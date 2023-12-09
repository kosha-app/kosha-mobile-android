package com.musica.dashboard


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.musica.common.compose.KoshaComposeActivity
import com.musica.common.compose.theme.BackgroundGradientColors
import com.musica.common.navigation.BottomNavItem
import com.musica.common.navigation.KoshaBottomNav
import com.musica.dashboard.home.HomeScreen
import com.musica.dashboard.home.viewmodel.DashboardViewModel
import com.musica.dashboard.search.ui.SearchScreen
import com.musica.dashboard.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardActivity : KoshaComposeActivity() {

    @Composable
    override fun ActivityContent() {
        val navController = rememberNavController()
        val viewModel: DashboardViewModel = viewModel()
        val searchViewModel: SearchViewModel = viewModel()

        Scaffold(
            bottomBar = {
                KoshaBottomNav(navController = navController)
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(brush = Brush.verticalGradient(BackgroundGradientColors))
            ) {
                NavHost(navController, startDestination = BottomNavItem.Home.route) {
                    composable(BottomNavItem.Home.route) {
                        HomeScreen(
                            viewModel = viewModel,
                            onBackPressed = { finishAffinity() }
                        )
                    }
                    composable(BottomNavItem.Search.route) {
                        SearchScreen(searchViewModel)
                    }
                    composable(BottomNavItem.Premium.route) {
                        //TODO to implement Premium Screen
                    }
                    composable(BottomNavItem.YourLibrary.route) {
                        //TODO to implement Library Screen
                    }
                }
            }
        }
    }
}