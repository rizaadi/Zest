package com.zephysus.zest.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zephysus.zest.component.navigation.ZestBottomNavigation
import com.zephysus.zest.ui.Screen
import com.zephysus.zest.ui.screens.home.HomeScreen
import com.zephysus.zest.ui.screens.quotes.AddQuoteScreen
import com.zephysus.zest.ui.screens.quotes.DetailQuoteScreen
import com.zephysus.zest.ui.screens.quotes.DetailQuoteViewModel
import com.zephysus.zest.ui.screens.quotes.QuotesScreen
import com.zephysus.zest.ui.screens.settings.SettingsScreen
import com.zephysus.zest.utils.assistedViewModel

const val ZEST_NAV_HOST_ROUTE = "zest-main-route"

@Composable
fun ZestNavigation() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: Screen.Home.route

    NavHost(navController, startDestination = Screen.Home.route, route = ZEST_NAV_HOST_ROUTE) {
        composable(
            Screen.Home.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            HomeScreen(viewModel = hiltViewModel(), onNavigateToAddQuote = {
                navController.navigateToAddQuote()
            }, onNavigateToDetailQuote = {
                navController.navigateToDetailQuote(it)
            }, bottomNavigation = {
                ZestBottomNavigation(currentRoute = currentRoute,
                    onNavigateToSettings = { navController.navigateToSettings() },
                    onNavigateToAddQuote = { navController.navigateToAddQuote() },
                    onNavigateToQuotes = {
                        navController.navigateToQuotes()
                    })
            })
        }
        composable(
            Screen.Settings.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            SettingsScreen(viewModel = hiltViewModel(),
                onNavigateUp = { navController.navigateUp() })
        }
        composable(
            Screen.AddQuote.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            AddQuoteScreen(
                viewModel = hiltViewModel(),
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(
            Screen.Quotes.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            QuotesScreen(
                viewModel = hiltViewModel(),
                onNavigateToUp = {
                    navController.navigateUp()
                },
                onNavigateToDetailQuote = { id -> navController.navigateToDetailQuote(id) },
            )
        }
        composable(
            Screen.DetailQuote.route,
            arguments = listOf(
                navArgument(Screen.DetailQuote.ARG_QUOTE_ID) {
                    type = NavType.StringType
                },
            ),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
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
 * Navigates to Home Screen
 */
fun NavController.navigateToHome() = navigate(Screen.Home.route) {
    launchSingleTop = true
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
 * Navigates to Settings Screen
 */
fun NavController.navigateToSettings() = navigate(Screen.Settings.route) {
    launchSingleTop = true
}

/**
 * Navigates to Quotes Screen
 */
fun NavController.navigateToQuotes() = navigate(Screen.Quotes.route) {
    launchSingleTop = true
}


/**
 * Clears backstack including current screen and navigates to Quotes Screen
 */
//fun NavController.popAllAndNavigateToQuotes() = navigate(Screen.Quotes.route) {
//    launchSingleTop = true
//    popUpTo(ZEST_NAV_HOST_ROUTE)
//}
