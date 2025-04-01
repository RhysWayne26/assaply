package com.example.assaply.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.assaply.data.domain.model.Article

@Database(entities = [Article::class], version=2)
@TypeConverters(NewsTypeConverter::class)
abstract class NewsDatabase: RoomDatabase() {
    abstract val newsDao: NewsDao
}