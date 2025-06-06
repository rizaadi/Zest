package com.zephysus.zest.ui

sealed class Screen(val route: String) {
    object Quotes : Screen("quotes")
    object AddQuote : Screen("add_quote")
    object DetailQuote : Screen("quote/{quoteId}") {
        fun route(quoteId: String) = "quote/$quoteId"
        const val ARG_QUOTE_ID: String = "quoteId"
    }
}