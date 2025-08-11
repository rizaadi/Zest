package com.zephysus.zest.ui.screens.quotes

import androidx.lifecycle.viewModelScope
import com.zephysus.core.di.LocalRepository
import com.zephysus.core.model.Quote
import com.zephysus.core.repository.QuoteRepository
import com.zephysus.zest.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    @LocalRepository private val localRepository: QuoteRepository,
) : BaseViewModel<QuotesState>(initialState = QuotesState()) {

    init {
        observeQuotes()
    }

    private fun observeQuotes() {
        localRepository.getAllQuotes().distinctUntilChanged().onEach { res ->
            res.onSuccess { quotes ->
                setState {
                    it.copy(
                        isLoading = false,
                        quotes = quotes,
                    )
                }
            }.onFailure { message ->
                setState {
                    it.copy(
                        isLoading = false, error = message
                    )
                }
            }
        }.onStart { setState { it.copy(isLoading = true) } }.launchIn(viewModelScope)
    }
}