package com.mtanmay.stupa.ui.states

data class SignUpUIState(
    val signUpInProcess: Boolean = false,
    val userAlreadyExists: Boolean = false
)
