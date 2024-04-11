package com.mtanmay.stupa.ui.screens.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.mtanmay.stupa.utils.PreferencesManager
import com.mtanmay.stupa.viewmodels.AuthViewModel

@Composable
fun SignupRoute(
    onSignup: () -> Unit,
    onSignIn: () -> Unit,
    viewmodel: AuthViewModel,
    prefManager: PreferencesManager
) {
    val state by viewmodel.signUpState.collectAsState()

    SignupUI(
        signUpInProcess = state.signUpInProcess,
        userAlreadyExists = state.userAlreadyExists,
        onSignup = {
            viewmodel.resetSignUpUIState()
            viewmodel.signUp(it, prefManager) { exists ->
                if (!exists)
                    onSignup()
            }
        },
        onSignIn = onSignIn
    )
}