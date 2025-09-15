package com.zephysus.zest.ui.screens.quotes


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zephysus.core.model.Quote
import com.zephysus.zest.R
import com.zephysus.zest.component.ZestScaffold
import com.zephysus.zest.component.ZestTopAppBar
import com.zephysus.zest.ui.theme.InstrumentTypography
import com.zephysus.zest.ui.theme.Typography
import com.zephysus.zest.ui.theme.ZestTheme
import com.zephysus.zest.ui.theme.blackBg2
import com.zephysus.zest.ui.theme.borderDark
import com.zephysus.zest.ui.theme.borderLight
import com.zephysus.zest.ui.theme.whiteBg2

@Composable
fun QuotesScreen(
    viewModel: QuotesViewModel,
    onNavigateToUp: () -> Unit,
    onNavigateToDetailQuote: (String) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    QuotesContent(
        quotes = state.quotes,
        isLoading = state.isLoading,
        onNavigateToDetailQuote = onNavigateToDetailQuote,
        onNavigateUp = onNavigateToUp
    )
}

@Composable
fun QuotesContent(
    quotes: List<Quote>,
    isLoading: Boolean,
    onNavigateToDetailQuote: (String) -> Unit,
    onNavigateUp: () -> Unit,
) {
    ZestScaffold(
        isLoading = isLoading,
        zestTopAppBar = {
            ZestTopAppBar(
                title = "Quotes", onNavigateUp = onNavigateUp
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(quotes) { quote ->
                    QuoteCard(
                        quote = quote, onNavigateToDetailQuote = onNavigateToDetailQuote
                    )
                }
            }
        },

        )
}

@Composable
private fun QuoteCard(
    quote: Quote,
    onNavigateToDetailQuote: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSystemInDarkTheme()) blackBg2 else whiteBg2)
            .border(
                width = 1.dp,
                color = if (isSystemInDarkTheme()) borderDark else borderLight,
                shape = RoundedCornerShape(20.dp)
            )
            .fillMaxWidth()
            .clickable {
                onNavigateToDetailQuote(quote.id)
            }) {
        Image(
            painter = if (isSystemInDarkTheme()) painterResource(R.drawable.quotation) else painterResource(
                R.drawable.quotation_dark
            ),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 12.dp, top = 12.dp)
                .size(20.dp)
        )

        if (quote.isFeatured) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 12.dp, end = 12.dp)
                    .background(
                        color = if (isSystemInDarkTheme()) Color.White.copy(alpha = 0.05f) else Color.Black.copy(
                            alpha = 0.05f
                        ), shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "Featured",
                    style = Typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(
                    start = 15.dp,
                    end = if (quote.isFeatured) 70.dp else 35.dp, // Leave space for featured tag
                    top = 35.dp, // Leave space for quotation icon
                    bottom = 15.dp
                )
        ) {
            Text(
                text = quote.title,
                style = InstrumentTypography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 0.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = quote.author,
                style = Typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 0.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuoteCardLightPreview() {
    ZestTheme {
        QuoteCard(
            quote = Quote(
                id = "1",
                title = "The only way to do great work is to love what you do.",
                author = "Steve Jobs",
                isFeatured = true,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ), onNavigateToDetailQuote = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun QuoteCardDarkPreview() {
    ZestTheme(darkTheme = true) {
        QuoteCard(
            quote = Quote(
                id = "1",
                title = "The only way to do great work is to love what you do.",
                author = "Steve Jobs",
                isFeatured = true,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ), onNavigateToDetailQuote = {})
    }
}

@Preview(showBackground = true)
@Composable
fun QuotesContentLightPreview() {
    val dummyQuotes = listOf(
        Quote(
            id = "1",
            title = "Be yourself; everyone else is already taken.",
            author = "Oscar Wilde",
            isFeatured = false,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        ), Quote(
            id = "2",
            title = "The future belongs to those who believe in the beauty of their dreams and have the courage to pursue them with unwavering determination.",
            author = "Albert Einstein",
            isFeatured = true,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        ), Quote(
            id = "3",
            title = "The only way to do great work is to love what you do.",
            author = "Steve Jobs",
            isFeatured = false,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
    )
    ZestTheme {
        QuotesContent(
            quotes = dummyQuotes,
            isLoading = false,
            onNavigateToDetailQuote = {},
            onNavigateUp = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun QuotesContentDarkPreview() {
    val dummyQuotes = listOf(
        Quote(
            id = "1",
            title = "Be yourself; everyone else is already taken.",
            author = "Oscar Wilde",
            isFeatured = false,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        ), Quote(
            id = "2",
            title = "The future belongs to those who believe in the beauty of their dreams and have the courage to pursue them with unwavering determination.",
            author = "Albert Einstein",
            isFeatured = true,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        ), Quote(
            id = "3",
            title = "The only way to do great work is to love what you do.",
            author = "Steve Jobs",
            isFeatured = false,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
    )
    ZestTheme(darkTheme = true) {
        QuotesContent(
            quotes = dummyQuotes,
            isLoading = false,
            onNavigateToDetailQuote = {},
            onNavigateUp = {})
    }
}
