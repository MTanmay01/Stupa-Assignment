package com.mtanmay.data.repository

import com.mtanmay.data.db.UserDAO
import com.mtanmay.data.di.DispatchersModule.Dispatcher
import com.mtanmay.data.di.DispatchersModule.DispatcherType
import com.mtanmay.data.mappers.toLocalUser
import com.mtanmay.data.models.DBUser
import com.mtanmay.domain.models.LocalUser
import com.mtanmay.domain.models.interfaces.User
import com.mtanmay.domain.repository.IUserAuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserAuthRepositoryImpl @Inject constructor(
    private val userDAO: UserDAO,
    @Dispatcher(DispatcherType.IO) private val ioDispatcher: CoroutineDispatcher
) : IUserAuthRepository {

    override suspend fun saveUser(user: User) = withContext(ioDispatcher) {
        require(user is DBUser) {
            "UserDao only supports saving [DBUser] to the database"
        }
        userDAO.addUser(user)
    }

    override suspend fun signIn(emailId: String, password: String): LocalUser? = withContext(ioDispatcher) {
        userDAO.getUser(emailId, password)?.toLocalUser()
    }

    override suspend fun userAlreadyExists(emailId: String): Boolean {
        return userDAO.checkEmailRegistered(emailId) != null
    }
}