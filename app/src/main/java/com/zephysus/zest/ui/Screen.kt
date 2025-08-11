package com.zephysus.zest.ui

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Quotes : Screen("quotes")
    object AddQuote : Screen("add_quote")
    object Settings : Screen("settings")
    object DetailQuote : Screen("quote/{quoteId}") {
        fun route(quoteId: String) = "quote/$quoteId"
        const val ARG_QUOTE_ID: String = "quoteId"
    }
}