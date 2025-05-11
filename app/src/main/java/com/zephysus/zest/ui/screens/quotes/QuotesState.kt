package com.zephysus.zest.ui.screens.quotes

import androidx.compose.runtime.Immutable
import com.zephysus.core.model.Quote
import com.zephysus.zest.ui.State

@Immutable
data class QuotesState(
    val isLoading: Boolean = false,
    val quotes: List<Quote> = emptyList(),
    val error: String? = null,
) : State