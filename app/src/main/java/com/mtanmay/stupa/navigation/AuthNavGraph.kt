package com.mtanmay.stupa.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mtanmay.stupa.ui.screens.signin.SignInRoute
import com.mtanmay.stupa.ui.screens.signup.SignupRoute
import com.mtanmay.stupa.utils.PreferencesManager
import com.mtanmay.stupa.viewmodels.AuthViewModel

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        route = NavGraph.AUTHENTICATION,
        startDestination = AuthScreen.SIGNIN.route
    ) {
        composable(AuthScreen.SIGNUP.route) {
            val context = LocalContext.current

            val vm: AuthViewModel = hiltViewModel()
            val prefManager = PreferencesManager(context)

            SignupRoute(
                viewmodel = vm,
                prefManager = prefManager,
                onSignup = {
                    navController.navigate(
                        route = RootScreen.HOME.route,
                        navOptions = NavOptions.Builder()
                            .setPopUpTo(route = NavGraph.AUTHENTICATION, inclusive = true)
                            .build()
                    )
                },
                onSignIn = {
                    navController.popBackStack()
                }
            )
        }
        composable(AuthScreen.SIGNIN.route) {
            val context = LocalContext.current

            val vm: AuthViewModel = hiltViewModel()
            val prefManager = PreferencesManager(context)

            SignInRoute(
                viewModel = vm,
                prefManager = prefManager,
                onSignUp = {
                    navController.navigate(AuthScreen.SIGNUP.route)
                },
                onSignInSuccessful = {
                    navController.navigate(
                        route = RootScreen.HOME.route,
                        navOptions = NavOptions.Builder()
                            .setPopUpTo(route = NavGraph.AUTHENTICATION, inclusive = true)
                            .build()
                    )
                }
            )
        }
    }
}

sealed class AuthScreen(val route: String) {
    data object SIGNIN : AuthScreen("SIGN_IN")
    data object SIGNUP : AuthScreen("SIGN_UP")
}