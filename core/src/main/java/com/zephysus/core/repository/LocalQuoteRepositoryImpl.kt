package com.zephysus.core.repository

import com.zephysus.core.model.Quote
import com.zephysus.data.local.dao.QuoteEntity
import com.zephysus.data.local.dao.QuotesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

/**
 * Source of data of quotes from from local database
 */
class LocalQuoteRepositoryImpl @Inject constructor(
    private val quotesDao: QuotesDao,
) : QuoteRepository {

    override fun getQuoteById(quoteId: String): Flow<Quote> =
        quotesDao.getQuoteById(quoteId).filterNotNull()
            .map { Quote(it.quoteId, it.title, it.author, it.createdAt, it.updatedAt) }

    override fun getAllQuotes(): Flow<Either<List<Quote>>> = quotesDao.getAllQuotes()
        .map { quotes -> quotes.map { Quote(it.quoteId, it.title, it.author, it.createdAt, it.updatedAt) } }
        .transform { quotes -> emit(Either.success(quotes)) }
        .catch { emit(Either.success(emptyList())) }

    override suspend fun addQuote(title: String, author: String): Either<String> =
        kotlin.runCatching {
            val tempQuoteId = QuoteRepository.generateTemporaryId()
            quotesDao.addQuote(
                QuoteEntity(
                    quoteId = tempQuoteId,
                    title = title,
                    author = author,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                )
            )
            Either.success(tempQuoteId)
        }.getOrDefault(Either.error("Unable to create a new quote"))

    override suspend fun addQuotes(quotes: List<Quote>) = quotes.map {
        QuoteEntity(
            quoteId = it.id,
            title = it.title,
            author = it.author,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt,
        )
    }.let {
        quotesDao.addQuotes(it)
    }

    override suspend fun updateQuote(
        quoteId: String,
        title: String,
        author: String,
    ): Either<String> = runCatching {
        quotesDao.updateQuoteById(quoteId, title, author)
        Either.success(quoteId)
    }.getOrDefault(Either.error("Unable to update quote"))

    override suspend fun deleteQuote(quoteId: String): Either<String> = runCatching {
        quotesDao.deleteQuoteById(quoteId)
        Either.success(quoteId)
    }.getOrDefault(Either.error("Unable to delete quote"))

    override suspend fun deleteAllQuotes() = quotesDao.deleteAllQuotes()

    override suspend fun updateQuoteId(oldQuoteId: String, newQuoteId: String) =
        quotesDao.updateQuoteId(oldQuoteId, newQuoteId)
}