package com.zephysus.zest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.zephysus.core.repository.UserDataRepository
import com.zephysus.zest.navigation.ZestNavigation
import com.zephysus.zest.ui.screens.quotes.DetailQuoteViewModel
import com.zephysus.zest.ui.theme.ZestTheme
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userDataRepository: UserDataRepository

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun quoteDetailViewModelFactory(): DetailQuoteViewModel.Factory
    }

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val uiState by viewModel.uiState.collectAsState()

            val darkTheme = shouldUseDarkTheme(uiState)

            // Update system bars based on theme
            UpdateSystemBars(darkTheme = darkTheme)

            ZestTheme(darkTheme = darkTheme) {
                ZestNavigation()
            }
        }
    }

    @Composable
    private fun shouldUseDarkTheme(uiState: MainActivityUiState): Boolean {
        return uiState.shouldUseDarkTheme(isSystemInDarkTheme())
    }

    @Composable
    private fun UpdateSystemBars(darkTheme: Boolean) {
        SideEffect {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    lightScrim = android.graphics.Color.TRANSPARENT,
                    darkScrim = android.graphics.Color.TRANSPARENT,
                ) { darkTheme },
                navigationBarStyle = SystemBarStyle.auto(
                    lightScrim = lightScrim,
                    darkScrim = darkScrim,
                ) { darkTheme },
            )
        }
    }
}

private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)