package com.mtanmay.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mtanmay.data.models.DBUser

@Database(
    entities = [DBUser::class],
    version = 1
)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun userDao(): UserDAO
}