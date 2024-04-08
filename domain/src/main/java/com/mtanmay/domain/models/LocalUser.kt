package com.mtanmay.domain.models

import com.mtanmay.domain.models.interfaces.User

data class LocalUser(
    val username: String,
    val emailId: String,
    val phoneNumber: String,
    val country: String,
    private val mToken: Int = -1
): User {

    override val token: Int
        get() = mToken

    companion object {
        /**
         * dummy user created to avoid signing in everytime during development
         */
        val dummy = LocalUser(
        username = "NA",
        emailId = "NA",
        phoneNumber = "NA",
        country = "NA",
        )
    }
}