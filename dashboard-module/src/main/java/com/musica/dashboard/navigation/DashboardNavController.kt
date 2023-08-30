package com.musica.dashboard.navigation

import androidx.compose.runtime.Composable
import com.musica.common.animations.SlideInAnimationVisibility
import com.musica.dashboard.player.MusicPlayer

//package com.musica.dashboard.navigation
//
//import androidx.compose.animation.AnimatedContent
//import androidx.compose.animation.AnimatedContentScope
//import androidx.compose.animation.AnimatedContentTransitionScope
//import androidx.compose.animation.EnterTransition
//import androidx.compose.animation.core.FiniteAnimationSpec
//import androidx.compose.animation.core.LinearOutSlowInEasing
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.slideInVertically
//import androidx.compose.animation.slideOutVertically
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.unit.IntOffset
//import androidx.navigation.NavBackStackEntry
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import com.musica.dashboard.home.DashboardScreen
//import com.musica.dashboard.player.MusicPlayer
//
//@Composable
//fun com.musica.dashboard.navigation.DashboardNavController() {
//
//    val navController = rememberNavController()
//
//
//    NavHost(navController = navController, startDestination = DestinationKey.DASHBOARD.name){
//        composable(
//            route = DestinationKey.DASHBOARD.name,
//            enterTransition = {
//                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(450))
//            },
//            exitTransition = {
//                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, tween(450, delayMillis = 600) )
//            }
//        ){
//            DashboardScreen()
//        }
//        composable(
//            route = DestinationKey.MUSIC_PLAYER.name,
//            enterTransition = {
//                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(450))
//            },
//            exitTransition = {
//                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, tween(450) )
//            }
//        ){
//            MusicPlayer()
//        }
//    }
//
//}
//
//enum class DestinationKey{
//    DASHBOARD,
//    MUSIC_PLAYER
//}


//object DashboardNavController {
//    @Composable
//    fun navController(showMusicPlayer: Boolean) = SlideInAnimationVisibility(showMusicPlayer) {
//        MusicPlayer()
//    }
//}
