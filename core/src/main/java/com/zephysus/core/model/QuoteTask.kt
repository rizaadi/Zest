package com.zephysus.core.model

class QuoteTask private constructor(val quoteId: String, val action: QuoteTaskAction) {
    companion object {
        fun create(quoteId: String) = QuoteTask(quoteId, QuoteTaskAction.CREATE)
        fun update(quoteId: String) = QuoteTask(quoteId, QuoteTaskAction.UPDATE)
        fun delete(quoteId: String) = QuoteTask(quoteId, QuoteTaskAction.DELETE)
        fun pin(quoteId: String) = QuoteTask(quoteId, QuoteTaskAction.PIN)
    }
}

enum class QuoteTaskAction {
    CREATE, UPDATE, DELETE, PIN
}