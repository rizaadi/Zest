package com.zephysus.zest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.zephysus.zest.navigation.ZestNavigation
import com.zephysus.zest.ui.theme.ZestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZestTheme {
                Surface {
                    ZestNavigation()
                }
            }
        }
    }
}
