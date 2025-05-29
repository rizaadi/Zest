package com.zephysus.zest.ui.screens.quotes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zephysus.zest.component.scaffold.ZestScaffold
import com.zephysus.zest.component.scaffold.ZestTopAppBar

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
        showSaveButton = state.showSave,
        onTitleChange = viewModel::setTitle,
        onAuthorChange = viewModel::setAuthor,
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
    showSaveButton: Boolean,
    onTitleChange: (String) -> Unit,
    onAuthorChange: (String) -> Unit,
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
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title, onValueChange = onTitleChange,
                    label = {
                        Text(text = "Title")
                    },
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = author, onValueChange = onAuthorChange,
                    label = {
                        Text(text = "Author")
                    },
                )
                Button(
                    onClick = onClickAddQuote,
                    enabled = showSaveButton,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                ) {
                    Text(text = "Save")
                }
            }
        },
    )
}