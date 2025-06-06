package com.zephysus.zest.ui.screens.quotes

import androidx.compose.runtime.Immutable
import com.zephysus.zest.ui.State

@Immutable
data class AddQuotesState(
    val title: String = "",
    val author: String = "",
    val isFeatured: Boolean = false,
    val showSave: Boolean = false,
    val isValidate: Boolean = false,
    val isLoading: Boolean = false,
    val added: Boolean = false,
    val errorMessage: String? = null,
) : State