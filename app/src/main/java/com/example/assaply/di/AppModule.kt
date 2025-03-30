package com.example.assaply.di

import android.app.Application
import com.example.assaply.data.domain.manager.LocalUserManager
import com.example.assaply.data.domain.manager.LocalUserManagerImplementation
import com.example.assaply.data.domain.remote.NewsApi
import com.example.assaply.data.domain.remote.repo.NewsRepositoryImplementation
import com.example.assaply.data.domain.usecases.app_entry.AppEntryUsecases
import com.example.assaply.data.domain.usecases.app_entry.ReadAppEntry
import com.example.assaply.data.domain.usecases.app_entry.SaveAppEntry
import com.example.assaply.data.domain.usecases.news.GetNews
import com.example.assaply.data.domain.usecases.news.NewsUsecases
import com.example.assaply.repo.NewsRepository
import com.example.assaply.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    @Provides
    @Singleton
    fun provideNewsUsecases(newsRepository: NewsRepository): NewsUsecases {
        return NewsUsecases(
            getNews = GetNews(newsRepository)
        )
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsApi : NewsApi): NewsRepository = NewsRepositoryImplementation(newsApi)

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }
}