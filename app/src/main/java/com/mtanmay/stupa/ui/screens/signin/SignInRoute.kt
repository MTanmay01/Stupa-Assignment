package com.mtanmay.stupa.ui.screens.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.mtanmay.stupa.utils.PreferencesManager
import com.mtanmay.stupa.viewmodels.AuthViewModel

@Composable
fun SignInRoute(
    viewModel: AuthViewModel,
    prefManager: PreferencesManager,
    onSignInSuccessful: () -> Unit = {},
    onSignUp: () -> Unit = {}
) {
    val state by viewModel.userFlow.collectAsState()

    LaunchedEffect(state.user) {
        if (state.user != null) {
            prefManager.saveUserToken(state.user!!.token)
            onSignInSuccessful()
        }
    }

    SignInUI(
        isLoading = state.loading,
        isInvalidCredential = state.failed,
        onResetState = viewModel::resetSignInUIState,
        onSignIn = { email, password ->
            if (email.isNotBlank() && password.isNotBlank()) {
                viewModel.signIn(email, password)
            }
        },
        onSignUp = {
            viewModel.resetSignInUIState()
            onSignUp()
        }
    )

}