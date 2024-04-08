package com.mtanmay.stupa.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mtanmay.stupa.ui.screens.profile.ProfileRoute
import com.mtanmay.stupa.ui.screens.users.UsersRoute
import com.mtanmay.stupa.utils.PreferencesManager
import com.mtanmay.stupa.viewmodels.HomeViewModel

@Composable
fun HomeNavGraph(
    viewmodel: HomeViewModel,
    preferenceManager: PreferencesManager,
    navController: NavHostController,
    onLogOut: () -> Unit = {}
) {
    NavHost(
        route = NavGraph.HOME,
        navController = navController,
        startDestination = HomeScreen.USERS.route
    ) {
        composable(HomeScreen.USERS.route) {
            UsersRoute(viewmodel)
        }
        composable(HomeScreen.PROFILE.route) {
            ProfileRoute(viewmodel, preferenceManager) {
                onLogOut()
            }
        }
    }
}

sealed class HomeScreen(val route: String) {
    data object USERS: HomeScreen("USERS")
    data object PROFILE: HomeScreen("PROFILE")
}