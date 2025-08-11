package com.zephysus.zest.ui.screens.home

import androidx.compose.runtime.Immutable
import com.zephysus.core.model.Quote
import com.zephysus.zest.ui.State

@Immutable
data class HomeState(
    val isLoading: Boolean = false,
    val featuredQuotes: List<Quote> = emptyList(),
    val error: String? = null,
) : State