package com.mtanmay.stupa.ui.states

import com.mtanmay.domain.models.LocalUser

data class RegisteredUsersUIState(
    val loading: Boolean = true,
    val users: List<LocalUser> = emptyList()
)
