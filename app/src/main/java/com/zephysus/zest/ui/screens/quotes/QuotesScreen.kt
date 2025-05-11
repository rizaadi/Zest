package com.zephysus.zest.ui.screens.quotes


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zephysus.core.model.Quote
import com.zephysus.zest.component.scaffold.ZestScaffold
import com.zephysus.zest.component.scaffold.ZestTopAppBar

@Composable
fun QuotesScreen(
    viewModel: QuotesViewModel,
    onNavigateToAddQuote: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    QuotesContent(
        quotes = state.quotes, onNavigateToAddQuote = onNavigateToAddQuote
    )
}

@Composable
fun QuotesContent(
    quotes: List<Quote>,
    onNavigateToAddQuote: () -> Unit,
) {
    ZestScaffold(
        zestTopAppBar = {
            ZestTopAppBar()
        },
        content = {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(quotes) { quote ->
                    Card(
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .fillMaxWidth()
                            .wrapContentWidth()
                            .clickable { },
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = quote.title, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = quote.author)
                        }
                    }
                }
            }
        },
        floatingActionButton = { onNavigateToAddQuote() },
    )
}