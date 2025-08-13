package com.zephysus.zest.ui.screens.home

import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spartapps.swipeablecards.state.rememberSwipeableCardsState
import com.spartapps.swipeablecards.ui.SwipeableCardsFactors
import com.spartapps.swipeablecards.ui.SwipeableCardsProperties
import com.spartapps.swipeablecards.ui.animation.SwipeableCardsAnimations
import com.spartapps.swipeablecards.ui.lazy.LazySwipeableCards
import com.spartapps.swipeablecards.ui.lazy.items
import com.zephysus.core.model.Quote
import com.zephysus.zest.R
import com.zephysus.zest.component.scaffold.ZestScaffold
import com.zephysus.zest.ui.theme.blackBg2
import com.zephysus.zest.ui.theme.instrumentFamily

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToAddQuote: () -> Unit,
    onNavigateToDetailQuote: (String) -> Unit,
    bottomNavigation: @Composable () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()

    HomeContent(
        featuredQuotes = state.featuredQuotes,
        isLoading = state.isLoading,
        swipeCounter = state.swipeCounter,
        onQuoteSwiped = viewModel::onQuoteSwiped,
        bottomNavigation = bottomNavigation
    )
}

@Composable
fun HomeContent(
    featuredQuotes: List<Quote>,
    isLoading: Boolean,
    swipeCounter: Int,
    onQuoteSwiped: (Quote) -> Unit,
    bottomNavigation: @Composable () -> Unit = {},
) {
    ZestScaffold(
        isLoading = isLoading, content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Top,
                ) {
                    Text("Featured Quotes", fontSize = 24.sp, fontFamily = instrumentFamily)
                    Spacer(modifier = Modifier.height(8.dp))

                    if (featuredQuotes.isEmpty()) {
                        // Show empty state
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(500.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No featured quotes available",
                                color = Color.Gray,
                                style = TextStyle(fontSize = 16.sp)
                            )
                        }
                    } else {
                        SwipeableQuoteCards(
                            quotes = featuredQuotes,
                            swipeCounter = swipeCounter,
                            onQuoteSwiped = onQuoteSwiped
                        )
                    }
                }
            }
        }, bottomBar = bottomNavigation
    )

}

@Composable
private fun SwipeableQuoteCards(
    quotes: List<Quote>,
    swipeCounter: Int,
    onQuoteSwiped: (Quote) -> Unit,
) {
    val keyValue = if (quotes.size == 1) {
        "${quotes.first().id}_$swipeCounter"
    } else {
        quotes.firstOrNull()?.id ?: "empty"
    }

    key(keyValue) {
        val state = rememberSwipeableCardsState(initialCardIndex = 0, itemCount = { quotes.size })

        LazySwipeableCards(
            modifier = Modifier.windowInsetsPadding(
                WindowInsets.systemBars.only(
                    WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                )
            ),
            state = state,
            onSwipe = { quote, _ -> onQuoteSwiped(quote) },
            properties = SwipeableCardsProperties(
                padding = 0.dp,
                swipeThreshold = 50.dp,
                lockBelowCardDragging = true,
                enableRotation = true,
                stackedCardsOffset = 50.dp,
                draggingAcceleration = 1.5f,
            ),
            factors = SwipeableCardsFactors(
                cardOffsetCalculation = { index, _, props ->
                    val offset = props.stackedCardsOffset.value * (index - state.currentCardIndex)
                    Offset(0f, offset)
                },
                scaleFactor = { index, _, _ ->
                    val current = state.currentCardIndex
                    val diff = index - current
                    val maxVisibleCards = 3
                    val scaleReductionPerCard = 0.03f

                    if (diff in 1..maxVisibleCards) {
                        1f - (diff * scaleReductionPerCard)
                    } else {
                        1f
                    }
                },
            ),
            animations = SwipeableCardsAnimations(
                cardsAnimationSpec = spring(dampingRatio = 0.6f, stiffness = 100f),
                rotationAnimationSpec = spring()
            )
        ) {
            items(quotes) { quote, _, _ ->
                QuoteItem(quote)
            }
        }
    }
}

@Composable
fun QuoteItem(quote: Quote) {
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .height(500.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                clip = false,
            )
            .clip(RoundedCornerShape(20.dp))
            .background(blackBg2)
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(20.dp)
            )
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.quotation),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 12.dp, top = 15.dp)
                .size(30.dp)
        )
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.Transparent)
                .align(Alignment.Center)
        ) {
            Text(
                text = quote.title,
                fontFamily = instrumentFamily,
                fontSize = 32.sp,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .fillMaxWidth(),
                color = Color.White,
                style = TextStyle(
                    lineHeight = 40.sp
                )
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = quote.author,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun QuoteItemPreview() {
    QuoteItem(
        quote = Quote(
            id = "1",
            title = "Stay hungry, stay foolish.",
            author = "Steve Jobs",
            isFeatured = false,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
    )
}

@Composable
@Preview(showBackground = true)
fun HomeContentPreview() {
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
            title = "In the middle of every difficulty lies opportunity.",
            author = "Albert Einstein",
            isFeatured = false,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
    )
    HomeContent(featuredQuotes = dummyQuotes,
        isLoading = false,
        swipeCounter = 0,
        onQuoteSwiped = {},
        bottomNavigation = {})
}
