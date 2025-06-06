package com.zephysus.zest.ui.screens.quotes

import androidx.compose.runtime.Immutable
import com.zephysus.zest.ui.State

@Immutable
data class DetailQuoteState(
    val title: String? = null,
    val author: String? = null,
    val isFeatured: Boolean = false,
    val isLoading: Boolean = false,
    val showSave: Boolean = false,
    val finished: Boolean = false,
    val errorMessage: String? = null,
) : State
