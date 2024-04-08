package com.mtanmay.data.di

import android.content.Context
import androidx.room.Room
import com.mtanmay.data.db.LocalDatabase
import com.mtanmay.data.db.UserDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDBModule {
    @Provides
    @Singleton
    fun providesRoomDatabase(@ApplicationContext context: Context): LocalDatabase =
        Room
            .databaseBuilder(
            context,
            LocalDatabase::class.java,
            "UserDatabase"
        )
            .createFromAsset("database/user.db")
            .build()

    @Provides
    fun providesUserDao(db: LocalDatabase): UserDAO =
        db.userDao()

}