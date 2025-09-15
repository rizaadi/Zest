package com.zephysus.zest.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zephysus.zest.component.dialog.FailureDialog
import com.zephysus.zest.component.dialog.LoaderDialog
import com.zephysus.zest.component.navigation.ZestBottomNavigation
import com.zephysus.zest.ui.theme.ZestTheme

@Composable
fun ZestScaffold(
    modifier: Modifier = Modifier,
    zestTopAppBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
    floatingActionButton: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    isLoading: Boolean = false,
    error: String? = null,
) {
    if (isLoading) {
        LoaderDialog()
    }
    if (error != null) {
        FailureDialog(error)
    }
    Scaffold(
        modifier = modifier,
        topBar = zestTopAppBar,
        content = content,
        floatingActionButton = floatingActionButton,
        bottomBar = {
            Box(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)) {
                bottomBar()
            }
        },
    )
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun ZestScaffoldLightPreview() {
    ZestTheme {
        ZestScaffold(
            zestTopAppBar = {
                ZestTopAppBar(title = "Preview Title", onNavigateUp = {})
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Scaffold Content Area")
                }
            },
            bottomBar = {
                ZestBottomNavigation(
                    "",
                    onNavigateToSettings = {},
                    onNavigateToAddQuote = { },
                    onNavigateToQuotes = {},
                )
            },
        )
    }
}

@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ZestScaffoldDarkPreview() {
    ZestTheme {
        ZestScaffold(
            zestTopAppBar = {
                ZestTopAppBar(title = "Preview Title", onNavigateUp = {})
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Scaffold Content Area")
                }
            },
            bottomBar = {
                ZestBottomNavigation(
                    "",
                    onNavigateToSettings = {},
                    onNavigateToAddQuote = { },
                    onNavigateToQuotes = {},
                )
            },
        )
    }
}

@Preview(name = "Loading State", showBackground = true)
@Composable
fun ZestScaffoldLoadingPreview() {
    ZestTheme {
        ZestScaffold(
            isLoading = true, content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Content (should be dimmed by loader)")
                }
            })
    }
}

@Preview(name = "Error State", showBackground = true)
@Composable
fun ZestScaffoldErrorPreview() {
    ZestTheme {
        ZestScaffold(
            error = "Something went wrong!", content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Content (should show error dialog)")
                }
            })
    }
}
