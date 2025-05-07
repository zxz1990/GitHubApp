package com.mcdull.githubapp.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.mcdull.githubapp.data.AuthRepository
import com.mcdull.githubapp.network.OAuthApi
import com.mcdull.githubapp.user.UserManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUserManager(prefs: SharedPreferences): UserManager {
        return UserManager(prefs)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(app)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: OAuthApi,
        prefs: SharedPreferences
    ): AuthRepository {
        return AuthRepository(apiService)
    }
}