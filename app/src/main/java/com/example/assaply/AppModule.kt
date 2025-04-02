package com.example.assaply

import android.app.Application
import androidx.room.Room
import com.example.assaply.data.api.NewsApi
import com.example.assaply.data.domain.NewsUsecases
import com.example.assaply.data.repository.NewsRepository
import com.example.assaply.data.repository.NewsRepositoryImplementation
import com.example.assaply.data.room.NewsDao
import com.example.assaply.data.room.NewsDatabase
import com.example.assaply.data.room.NewsTypeConverter
import com.example.assaply.util.Constants
import com.example.assaply.util.UserPreferences
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
    fun provideUserPreferences(app: Application): UserPreferences =
        UserPreferences(app)

    @Provides
    @Singleton
    fun provideNewsUsecases(
        repository: NewsRepository,
        dao: NewsDao
    ): NewsUsecases = NewsUsecases(repository, dao)

    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi): NewsRepository =
        NewsRepositoryImplementation(newsApi)

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi =
        retrofit.create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideNewsDatabase(app: Application): NewsDatabase =
        Room.databaseBuilder(
            context = app,
            klass = NewsDatabase::class.java,
            name = "news_db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideNewsDao(db: NewsDatabase): NewsDao = db.newsDao

    @Provides
    @Singleton
    fun provideNewsTypeConverter(): NewsTypeConverter = NewsTypeConverter()
}

