package com.bs.weathermobileappui.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bs.weathermobileappui.presentation.ui.home.HomeScreen
import com.bs.weathermobileappui.presentation.ui.theme.WeatherMobileAppUITheme
import com.bs.weathermobileappui.presentation.ui.theme.background
import com.bs.weathermobileappui.presentation.welcome.WelcomeScreen
import com.bs.weathermobileappui.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherMobileAppUITheme {
                val bottomNavigationScreens = listOf(
                    Screen.HomeScreen,
                    Screen.SearchScreen,
                    Screen.LikeScreen
                )
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        when (currentDestination?.route) {
                            Screen.HomeScreen.route,
                            Screen.SearchScreen.route,
                            Screen.LikeScreen.route -> {
                                BottomNavigation(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
                                    backgroundColor = Color.White
                                ) {
                                    bottomNavigationScreens.forEach { screen ->
                                        val selected = screen.route == navBackStackEntry?.destination?.route
                                        BottomNavigationItem(
                                            icon = {
                                                Icon(
                                                    painter = painterResource(id = screen.resourceId),
                                                    contentDescription = null,
                                                    tint = if (selected) Color.DarkGray else Color.LightGray,
                                                    modifier = Modifier
                                                        .size(20.dp)
                                                )
                                            },
                                            selected = selected,
                                            onClick = {
                                                navController.navigate(screen.route)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize(),
                    backgroundColor = background
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.HomeScreen.route
                        ) {
                            composable(Screen.WelcomeScreen.route) {
                                WelcomeScreen(onNavigate = {
                                    navController.navigate(Screen.HomeScreen.route)
                                })
                            }
                            composable(Screen.HomeScreen.route) {
                                HomeScreen()
                            }
                            composable(Screen.SearchScreen.route) {

                            }
                            composable(Screen.LikeScreen.route) {

                            }
                        }
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String, @DrawableRes val resourceId: Int) {
    object WelcomeScreen : Screen("welcome_screen", 0)
    object HomeScreen : Screen("home_screen", R.drawable.ic_home)
    object SearchScreen : Screen("search_screen", R.drawable.ic_search)
    object LikeScreen : Screen("like_screen", R.drawable.ic_like)
}