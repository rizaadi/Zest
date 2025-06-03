package com.zephysus.zest.utils.validator


object QuoteValidator {
    fun isValidQuote(title: String, author: String) =
        (title.trim().length >= 4 && author.isNotBlank())
}
