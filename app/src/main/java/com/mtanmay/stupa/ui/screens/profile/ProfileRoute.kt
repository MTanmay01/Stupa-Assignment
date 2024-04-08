package com.mtanmay.stupa.ui.screens.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.mtanmay.stupa.utils.PreferencesManager
import com.mtanmay.stupa.viewmodels.HomeViewModel

@Composable
fun ProfileRoute(
    viewmodel: HomeViewModel,
    preferenceManager: PreferencesManager,
    onLogOut: () -> Unit
) {
    val state by viewmodel.userFlow.collectAsState()

    ProfileUI(
        isLoading = state.loading,
        user = state.user
    ) {
        preferenceManager.removeUserToken()
        onLogOut()
    }

    LaunchedEffect(Unit) {
        val token = preferenceManager.loggedInUserToken()
        viewmodel.getUser(token)
    }
}