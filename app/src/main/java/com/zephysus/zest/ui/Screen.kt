package com.zephysus.zest.ui

sealed class Screen(val route: String) {
    object Quotes : Screen("quotes")
    object AddQuote : Screen("add_quote")
}