package com.mtanmay.stupa.ui.states

import com.mtanmay.domain.models.LocalUser

data class SignInUIState(
    val loading: Boolean = false,
    val failed: Boolean = false,
    val user: LocalUser? = null
)