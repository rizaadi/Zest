package com.zephysus.zest.ui.screens.quotes

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zephysus.zest.component.ZestScaffold
import com.zephysus.zest.component.ZestTopAppBar
import com.zephysus.zest.ui.theme.ZestTheme

@Composable
fun AddQuoteScreen(
    viewModel: AddQuoteViewModel,
    onNavigateUp: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    AddQuoteContent(
        isLoading = state.isLoading,
        title = state.title,
        author = state.author,
        isFeatured = state.isFeatured,
        showSaveButton = state.showSave,
        onTitleChange = viewModel::setTitle,
        onAuthorChange = viewModel::setAuthor,
        onFeaturedChange = viewModel::setFeatured,
        onClickAddQuote = viewModel::saveQuote,
        error = state.errorMessage,
        onNavigateUp = onNavigateUp,
    )

    LaunchedEffect(state.added) {
        if (state.added) {
            onNavigateUp()
        }
    }
}

@Composable
fun AddQuoteContent(
    isLoading: Boolean,
    title: String,
    author: String,
    isFeatured: Boolean,
    showSaveButton: Boolean,
    onTitleChange: (String) -> Unit,
    onAuthorChange: (String) -> Unit,
    onFeaturedChange: (Boolean) -> Unit,
    onClickAddQuote: () -> Unit,
    error: String?,
    onNavigateUp: () -> Unit,
) {
    ZestScaffold(
        error = error, isLoading = isLoading,
        zestTopAppBar = {
            ZestTopAppBar(
                title = "Add Quote",
                onNavigateUp = {
                    onNavigateUp()
                },
            )
        },
        content = { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Column(
                    Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                ) {
                    Text(text = "Quote")
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = title,
                        onValueChange = onTitleChange,
                        shape = MaterialTheme.shapes.medium,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Author")
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = author,
                        onValueChange = onAuthorChange,
                        shape = MaterialTheme.shapes.medium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .toggleable(
                                value = isFeatured,
                                onValueChange = onFeaturedChange,
                                role = Role.Switch
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Featured")
                        Switch(
                            checked = isFeatured,
                            onCheckedChange = null, // Handled by the Row's toggleable modifier
                        )
                    }
                }
                Button(
                    onClick = onClickAddQuote,
                    enabled = showSaveButton,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Text(text = "Save")
                }
            }
        },
    )
}

@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddQuoteContentPreview() {
    ZestTheme {
        AddQuoteContent(
            isLoading = false,
            title = "Be the change you wish to see in the world.",
            author = "Mahatma Gandhi",
            isFeatured = true,
            showSaveButton = true,
            onTitleChange = {},
            onAuthorChange = {},
            onFeaturedChange = {},
            onClickAddQuote = {},
            error = null,
            onNavigateUp = {},
        )
    }
}

