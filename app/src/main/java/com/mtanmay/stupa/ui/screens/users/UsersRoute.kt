package com.mtanmay.stupa.ui.screens.users

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.mtanmay.stupa.viewmodels.HomeViewModel

@Composable
fun UsersRoute(
    viewModel: HomeViewModel
) {
    val state by viewModel.registeredUsersFlow.collectAsState()

    UsersUI(
        isLoading = state.loading,
        users = state.users
    )
}