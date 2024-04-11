package com.mtanmay.domain.models

import com.mtanmay.domain.models.interfaces.User

data class LocalUser(
    override val username: String,
    override val emailId: String,
    override val phoneNumber: String,
    override val country: String,
    override val token: Int = -1
): User