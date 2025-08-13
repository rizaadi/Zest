package com.zephysus.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotesDao {
    @Query("SELECT * FROM quotes WHERE quoteId = :quoteId")
    fun getQuoteById(quoteId: String): Flow<QuoteEntity?>

    @Query("select* from quotes ORDER BY createdAt DESC")
    fun getAllQuotes(): Flow<List<QuoteEntity>>

    @Query("SELECT * FROM quotes WHERE isFeatured = 1 ORDER BY createdAt DESC")
    fun getAllFeaturedQuotes(): Flow<List<QuoteEntity>>

    @Insert
    suspend fun addQuote(quote: QuoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuotes(notes: List<QuoteEntity>)

    @Query("UPDATE quotes SET title = :title, author = :author , isFeatured = :isFeatured, updatedAt = :updatedAt WHERE quoteId = :quoteId")
    suspend fun updateQuoteById(
        quoteId: String,
        title: String,
        author: String,
        isFeatured: Boolean,
        updatedAt: Long = System.currentTimeMillis(),
    )

    @Query("DELETE FROM quotes WHERE quoteId = :quoteId")
    suspend fun deleteQuoteById(quoteId: String)

    @Query("DELETE FROM quotes")
    suspend fun deleteAllQuotes()

    @Query("UPDATE quotes SET quoteId = :newQuoteId WHERE quoteId = :oldQuoteId")
    fun updateQuoteId(oldQuoteId: String, newQuoteId: String)

}