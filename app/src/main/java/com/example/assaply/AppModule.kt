package com.example.assaply

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.assaply.data.api.NewsApi
import com.example.assaply.data.domain.NewsUsecases
import com.example.assaply.data.repository.local.SavedArticlesRepository
import com.example.assaply.data.repository.local.SavedArticlesRepositoryImpl
import com.example.assaply.data.repository.remote.RemoteNewsRepository
import com.example.assaply.data.repository.remote.RemoteNewsRepositoryImpl
import com.example.assaply.data.room.NewsDao
import com.example.assaply.data.room.NewsDatabase
import com.example.assaply.data.room.NewsTypeConverter
import com.example.assaply.util.Constants
import com.example.assaply.util.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        remoteRepo: RemoteNewsRepository,
        localRepo: SavedArticlesRepository

    ) = NewsUsecases(remoteRepo, localRepo)

    @Provides
    @Singleton
    fun provideLocalDataRepository(
        newsDao: NewsDao
    ): SavedArticlesRepository =
        SavedArticlesRepositoryImpl(newsDao)

    @Provides
    @Singleton
    fun provideRemoteDataRepository(
        newsApi: NewsApi,
        @ApplicationContext context: Context
    ): RemoteNewsRepository =
        RemoteNewsRepositoryImpl(newsApi, context)

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)


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
