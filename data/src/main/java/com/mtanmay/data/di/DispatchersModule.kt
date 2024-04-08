package com.mtanmay.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Dispatcher(DispatcherType.IO)
    fun providesIoDispatcher(): CoroutineDispatcher =
        Dispatchers.IO

    @Provides
    @Dispatcher(DispatcherType.MAIN)
    fun providesMainDispatcher(): CoroutineDispatcher =
        Dispatchers.Main

    @Provides
    @Dispatcher(DispatcherType.DEFAULT)
    fun providesDefaultDispatcher(): CoroutineDispatcher =
        Dispatchers.Default

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Dispatcher(val type: DispatcherType)

    enum class DispatcherType {
        IO, MAIN, DEFAULT
    }
}