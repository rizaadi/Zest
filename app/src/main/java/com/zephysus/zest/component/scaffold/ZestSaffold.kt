package com.zephysus.zest.component.scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zephysus.zest.component.dialog.FailureDialog
import com.zephysus.zest.component.dialog.LoaderDialog

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
        }
    )
}