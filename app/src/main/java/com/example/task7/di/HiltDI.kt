package com.example.task7.di

import com.example.task7.data.FirebaseUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltDI {

    @Provides
    @Singleton
    fun provideFireBaseUtils(): FirebaseUtils {
        return FirebaseUtils()
    }
}
