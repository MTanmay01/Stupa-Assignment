package com.mtanmay.stupa.utils

import com.mtanmay.data.models.DBUser

object Validator {

    fun isEmailValid(
        emailId: String
    ): Boolean {
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$".toRegex()
        return emailId.matches(emailRegex)
    }

    fun isPasswordValid(
        password: String,
        passwordConfirm: String
    ) = password == passwordConfirm

    fun isUserValid(user: DBUser): Boolean {
        return user.username.isNotBlank() &&
               user.emailId.isNotBlank() &&
               user.password.isNotBlank() &&
               user.country.isNotEmpty() &&
               user.phoneNumber.isNotBlank()
    }
}