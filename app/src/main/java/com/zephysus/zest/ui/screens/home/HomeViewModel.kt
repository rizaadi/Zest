package com.zephysus.zest.ui.screens.home

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
class HomeViewModel @Inject constructor(
    @LocalRepository private val localRepository: QuoteRepository,
) : BaseViewModel<HomeState>(initialState = HomeState()) {

    init {
        observeFeaturesQuotes()
    }

    private fun observeFeaturesQuotes() {
        localRepository.getAllFeaturedQuotes().distinctUntilChanged()
            .onStart { setState { it.copy(isLoading = true) } }.onEach { result ->
                result.onSuccess { quotes ->
                    setState {
                        it.copy(
                            isLoading = false, featuredQuotes = quotes, error = null
                        )
                    }
                }.onFailure { message ->
                    setState {
                        it.copy(
                            isLoading = false, error = message
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onQuoteSwiped(quote: Quote) {
        setState { state ->
            when {
                state.featuredQuotes.isEmpty() -> state
                state.featuredQuotes.size == 1 -> {
                    // Single quote: increment counter to reset UI
                    state.copy(swipeCounter = state.swipeCounter + 1)
                }

                else -> {
                    // Multiple quotes: cycle the swiped quote to end
                    val quotes = state.featuredQuotes
                    val newQuotes = quotes.filter { it.id != quote.id } + quote
                    state.copy(featuredQuotes = newQuotes)
                }
            }
        }
    }
}