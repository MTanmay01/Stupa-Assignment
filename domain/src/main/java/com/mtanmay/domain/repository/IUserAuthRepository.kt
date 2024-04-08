package com.mtanmay.domain.repository

import com.mtanmay.domain.models.interfaces.User

interface IUserAuthRepository {
    suspend fun saveUser(user: User)
    suspend fun signIn(emailId: String, password: String): User?
    suspend fun userAlreadyExists(emailId: String): Boolean
}