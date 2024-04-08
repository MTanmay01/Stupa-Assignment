package com.mtanmay.data.di

import com.mtanmay.data.repository.UserAuthRepositoryImpl
import com.mtanmay.data.repository.UsersInfoRepository
import com.mtanmay.domain.repository.IUserAuthRepository
import com.mtanmay.domain.repository.IUsersInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsUserAuthRepository(impl: UserAuthRepositoryImpl): IUserAuthRepository

    @Binds
    abstract fun bindsUsersInfoRepository(impl: UsersInfoRepository): IUsersInfoRepository
}