package com.zephysus.zest.component.scaffold

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZestTopAppBar(
    title: String = "Zest",
    onNavigateUp: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit) = {},
) {
    TopAppBar(title = {
        Text(
            text = title, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth()
        )
    }, navigationIcon = {
        if (onNavigateUp != null) {
            IconButton(
                modifier = Modifier.padding(start = 4.dp), onClick = { onNavigateUp() }
            ) {
                Icon(
                    Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Navigate back"
                )
            }
        }

    }, actions = actions)
}