package com.zephysus.zest.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zephysus.zest.ui.Screen
import com.zephysus.zest.ui.screens.quotes.AddQuoteScreen
import com.zephysus.zest.ui.screens.quotes.DetailQuoteScreen
import com.zephysus.zest.ui.screens.quotes.DetailQuoteViewModel
import com.zephysus.zest.ui.screens.quotes.QuotesScreen
import com.zephysus.zest.utils.assistedViewModel

const val ZEST_NAV_HOST_ROUTE = "zest-main-route"

@Composable
fun ZestNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.Quotes.route, route = ZEST_NAV_HOST_ROUTE) {
        composable(Screen.Quotes.route) {
            QuotesScreen(
                viewModel = hiltViewModel(),
                onNavigateToAddQuote = {
                    navController.navigateToAddQuote()
                },
                onNavigateToDetailQuote = {
                    navController.navigateToDetailQuote(it)
                },
            )
        }
        composable(Screen.AddQuote.route) {
            AddQuoteScreen(
                viewModel = hiltViewModel(),
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(
            Screen.DetailQuote.route, arguments = listOf(
                navArgument(Screen.DetailQuote.ARG_QUOTE_ID) {
                    type = NavType.StringType
                },
            )
        ) {
            val quoteId = requireNotNull(it.arguments?.getString(Screen.DetailQuote.ARG_QUOTE_ID))
            DetailQuoteScreen(
                viewModel = assistedViewModel {
                    DetailQuoteViewModel.provideFactory(quoteDetailViewModelFactory(), quoteId)
                },
                onNavigateUp = { navController.navigateUp() },
            )
        }
    }
}

/**
 * Navigates to Add Quote Screen
 */
fun NavController.navigateToAddQuote() = navigate(Screen.AddQuote.route)

/**
 * Navigates to Detail Quote Screen
 */
fun NavController.navigateToDetailQuote(quoteId: String) =
    navigate(Screen.DetailQuote.route(quoteId))

/**
 * Clears backstack including current screen and navigates to Quotes Screen
 */
//fun NavController.popAllAndNavigateToQuotes() = navigate(Screen.Quotes.route) {
//    launchSingleTop = true
//    popUpTo(ZEST_NAV_HOST_ROUTE)
//}
