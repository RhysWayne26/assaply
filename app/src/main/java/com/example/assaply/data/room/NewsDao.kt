package com.example.assaply.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assaply.data.domain.entities.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article)

    @Delete
    suspend fun delete(article: Article)

    @Query("select * from Article")
    fun getArticles(): Flow<List<Article>>

    @Query("select * from Article where url=:url")
    suspend fun getArticle(url: String): Article?
}