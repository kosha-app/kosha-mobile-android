package com.musica.common.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.musica.common.R
import com.musica.common.compose.theme.MusicaphoneTheme
import com.musica.common.compose.theme.Secondary
import com.musica.common.compose.theme.Tertiary

@Composable
fun KoshaBottomNav(navController: NavController) {
    Box(
        modifier = Modifier
            .background(color = Tertiary)

    ) {
        BottomNavigation(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        start = Offset(0f, 0f),
                        end = Offset.Infinite,
                        colors = listOf(
                            Color(0xFF1F2427),
                            Color(0xFF485057)
                        )
                    ),
                    shape = RoundedCornerShape(32.dp)
                )
                .shadow(10.dp, ambientColor = Secondary, shape = RoundedCornerShape(32.dp)),
            backgroundColor = Tertiary,
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            BottomNavItem::class.sealedSubclasses.forEach { item ->
                BottomNavigationItem(
                    selected = currentRoute == item.objectInstance?.route,
                    onClick = {
                        navController.navigate(item.objectInstance?.route.toString()) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Image(
                            painter = painterResource(
                                id = item.objectInstance?.iconId ?: R.drawable.home_nav_icon
                            ),
                            contentDescription = stringResource(id = R.string.music_nav_bar_navigation_icon_description_text),
                            Modifier
                                .width(20.dp)
                                .height(20.dp),
                            colorFilter = ColorFilter.tint(Secondary)
                        )
                    },
                    label = {
                        Text(
                            text = item.objectInstance?.label.toString(),
                            fontSize = 10.sp,
                            color = Secondary,
                            textAlign = TextAlign.Center
                        )
                    }
                )
            }
        }
    }
}

sealed class BottomNavItem(val route: String, val iconId: Int, val label: String) {
    object Home : BottomNavItem("homeScreen", R.drawable.home_nav_icon, "Home")
    object Search : BottomNavItem("searchScreen", R.drawable.search_nav_icon, "Search")
    object YourLibrary : BottomNavItem("yourLibraryScreen", R.drawable.library_nav_icon, "Library")
    object Premium : BottomNavItem("premiumScreen", R.drawable.premium_nav_icon, "Premium")
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    MusicaphoneTheme {
        Surface {
            KoshaBottomNav(navController = rememberNavController())
//            KoshaNavBar(homeOnClick = {}, isHome = true)
        }
    }
}