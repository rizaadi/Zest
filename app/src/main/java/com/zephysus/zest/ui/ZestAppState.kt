package com.zephysus.zest.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zephysus.core.repository.UserDataRepository
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberZestAppState(
    userDataRepository: UserDataRepository,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): ZestAppState {
    return remember(navController, coroutineScope, userDataRepository) {
        ZestAppState(
            navController = navController,
            coroutineScope = coroutineScope,
            userDataRepository = userDataRepository,
        )
    }
}

@Stable
class ZestAppState(
    val navController: NavHostController,
    userDataRepository: UserDataRepository,
    coroutineScope: CoroutineScope,
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)

    val currentDestination: NavDestination?
        @Composable get() {
            // Collect the currentBackStackEntryFlow as a state
            val currentEntry =
                navController.currentBackStackEntryFlow.collectAsState(initial = null)

            // Fallback to previousDestination if currentEntry is null
            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

//    val currentTopLevelDestination: TopLevelDestination?
//        @Composable get() {
//            return TopLevelDestination.entries.firstOrNull { topLevelDestination ->
//                currentDestination?.hasRoute(route = topLevelDestination.route) == true
//            }
//        }
}