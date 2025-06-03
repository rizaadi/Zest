package com.zephysus.core.repository

import com.zephysus.core.model.Quote
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Singleton

/**
 * Repository for quotes.
 */
@Singleton
interface QuoteRepository {
    /**
     * Returns a quote
     *
     * @param quoteId A quote ID.
     */
    fun getQuoteById(quoteId: String): Flow<Quote>

    /**
     * Returns all quotes.
     */
    fun getAllQuotes(): Flow<Either<List<Quote>>>

    /**
     * Adds a new quote
     *
     * @param title Title of a quote
     * @param author Author of a quote
     */
    suspend fun addQuote(title: String, author: String): Either<String>

    /**
     * Adds a list of quotes. Replaces quotes if already exists
     */
    suspend fun addQuotes(quotes: List<Quote>)

    /**
     * Updates a new quote having ID [quoteId]
     *
     * @param quoteId The Quote ID
     * @param title Title of a quote
     * @param author Author of a quote
     */
    suspend fun updateQuote(
        quoteId: String,
        title: String,
        author: String,
    ): Either<String>

    /**
     * Deletes a new quote having ID [quoteId]
     */
    suspend fun deleteQuote(quoteId: String): Either<String>

    /**
     * Deletes all quotes.
     */
    suspend fun deleteAllQuotes()

    /**
     * Updates ID of a quote
     */
    suspend fun updateQuoteId(oldQuoteId: String, newQuoteId: String)

    companion object {
        private const val PREFIX_TEMP_QUOTE_ID = "TMP"
        fun generateTemporaryId() = "$PREFIX_TEMP_QUOTE_ID-${UUID.randomUUID()}"
        fun isTemporaryQuote(quoteId: String) = quoteId.startsWith(PREFIX_TEMP_QUOTE_ID)
    }
}