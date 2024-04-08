package com.mtanmay.data.repository

import com.mtanmay.data.db.UserDAO
import com.mtanmay.data.di.DispatchersModule.Dispatcher
import com.mtanmay.data.di.DispatchersModule.DispatcherType
import com.mtanmay.data.mappers.toLocalUser
import com.mtanmay.domain.models.LocalUser
import com.mtanmay.domain.repository.IUsersInfoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersInfoRepository @Inject constructor(
    private val userDAO: UserDAO,
    @Dispatcher(DispatcherType.IO) private val ioDispatcher: CoroutineDispatcher
): IUsersInfoRepository {

    override suspend fun getUser(token: Int): LocalUser? = withContext(ioDispatcher) {
        return@withContext userDAO.getUserFromToken(token)?.toLocalUser()
    }

    override suspend fun getRegisteredUsers(): List<LocalUser> = withContext(ioDispatcher) {
        return@withContext userDAO.getAllUser()
            .map { it.toLocalUser() }
    }
}