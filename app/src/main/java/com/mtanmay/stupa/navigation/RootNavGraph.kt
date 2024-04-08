package com.mtanmay.stupa.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mtanmay.stupa.ui.screens.home.HomeUI
import com.mtanmay.stupa.ui.screens.home.TabNavigationItem
import com.mtanmay.stupa.ui.screens.splash.SplashUI

@Composable
fun RootNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        route = NavGraph.ROOT,
        startDestination = RootScreen.SPLASH.route
    ) {
        authNavGraph(navController)

        composable(RootScreen.HOME.route) {
            val tabList = remember {
                listOf(
                    TabNavigationItem(
                        "USERS",
                        Icons.Default.Home,
                        HomeScreen.USERS.route
                    ),
                    TabNavigationItem(
                        "PROFILE",
                        Icons.Default.Person,
                        HomeScreen.PROFILE.route
                    )
                )
            }

            HomeUI(
                tabList = tabList,
                onLogOut = {
                    navController.navigate(
                        NavGraph.AUTHENTICATION,
                        navOptions = NavOptions.Builder()
                            .setPopUpTo(NavGraph.ROOT, inclusive = false)
                            .build()
                    )
                }
            )
        }

        composable(RootScreen.SPLASH.route) {
            SplashUI {
                navController.navigate(
                    route = it,
                    navOptions = NavOptions.Builder()
                        .setPopUpTo(route = RootScreen.SPLASH.route, inclusive = true)
                        .build()
                )
            }
        }
    }
}

sealed class RootScreen(val route: String) {
    data object SPLASH: RootScreen("SPLASH")
    data object HOME: RootScreen("HOME_SCREEN")
}

object NavGraph {
    const val ROOT = "root_graph"
    const val HOME = "home_graph"
    const val AUTHENTICATION = "auth_graph"
}