package com.example.assaply.dependencyInjection

import android.app.Application
import com.example.assaply.data.domain.manager.LocalUserManager
import com.example.assaply.data.domain.manager.LocalUserManagerImplementation
import com.example.assaply.data.domain.usecases.AppEntryUsecases
import com.example.assaply.data.domain.usecases.ReadAppEntry
import com.example.assaply.data.domain.usecases.SaveAppEntry
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
    fun provideLocalUserManager(application: Application): LocalUserManager = LocalUserManagerImplementation(application)


    @Provides
    @Singleton
    fun provideAppEntryUsecases(localUserManager: LocalUserManager) =
        AppEntryUsecases(
            saveAppEntry = SaveAppEntry(localUserManager),
            readAppEntry = ReadAppEntry(localUserManager)
        )
}