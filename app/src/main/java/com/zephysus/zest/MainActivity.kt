package com.zephysus.zest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.zephysus.zest.navigation.ZestNavigation
import com.zephysus.zest.ui.screens.quotes.DetailQuoteViewModel
import com.zephysus.zest.ui.theme.ZestTheme
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun quoteDetailViewModelFactory(): DetailQuoteViewModel.Factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZestTheme {
                ZestNavigation()
            }
        }
    }
}
