package com.zephysus.zest.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zephysus.zest.ui.Screen
import com.zephysus.zest.ui.screens.quotes.QuotesScreen

const val ZEST_NAV_HOST_ROUTE = "zest-main-route"

@Composable
fun ZestNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.Quotes.route, route = ZEST_NAV_HOST_ROUTE) {
        composable(Screen.Quotes.route) {
            QuotesScreen(viewModel = hiltViewModel(), onNavigateToAddQuote = {})
        }
    }
}

/**
 * Clears backstack including current screen and navigates to Quotes Screen
 */
fun NavController.popAllAndNavigateToQuotes() = navigate(Screen.Quotes.route) {
    launchSingleTop = true
    popUpTo(ZEST_NAV_HOST_ROUTE)
}
