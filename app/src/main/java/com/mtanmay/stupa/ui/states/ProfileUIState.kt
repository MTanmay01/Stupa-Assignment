package com.mtanmay.stupa.ui.states

import com.mtanmay.domain.models.LocalUser

data class ProfileUIState(
    val loading: Boolean = true,
    val user: LocalUser? = null
)
