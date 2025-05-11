package com.zephysus.zest.ui

sealed class Screen(val route: String, val name: String) {
    object Quotes : Screen("quotes", "Quotes")
}