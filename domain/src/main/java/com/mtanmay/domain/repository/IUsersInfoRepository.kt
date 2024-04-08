package com.mtanmay.domain.repository

import com.mtanmay.domain.models.LocalUser

interface IUsersInfoRepository {
    suspend fun getUser(token: Int): LocalUser?
    suspend fun getRegisteredUsers(): List<LocalUser>
}