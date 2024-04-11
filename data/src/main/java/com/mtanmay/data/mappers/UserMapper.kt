package com.mtanmay.data.mappers

import com.mtanmay.data.models.DBUser
import com.mtanmay.domain.models.LocalUser

fun DBUser.toLocalUser() = LocalUser(
    username, emailId, phoneNumber, country, token
)