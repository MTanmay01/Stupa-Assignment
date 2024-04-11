package com.mtanmay.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mtanmay.data.models.DBUser

@Dao
interface UserDAO {

    @Insert
    suspend fun addUser(user: DBUser)

    @Delete
    suspend fun deleteUser(user: DBUser)

    @Query("DELETE FROM user WHERE token=:token")
    suspend fun deleteUserWithToken(token: String)

    @Query("SELECT * FROM user WHERE email_id=:emailId AND password=:password")
    suspend fun getUser(emailId: String, password: String): DBUser?

    @Query("SELECT * FROM user WHERE token=:token")
    suspend fun getUserFromToken(token: Int): DBUser?

    @Query("SELECT * FROM user ORDER BY id DESC")
    suspend fun getAllUser(): List<DBUser>

    @Query("SELECT * FROM user WHERE email_id=:emailId")
    suspend fun checkEmailRegistered(emailId: String): DBUser?
}