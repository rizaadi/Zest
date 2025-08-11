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
        observeQuotes()
    }

    private fun observeQuotes() {
        localRepository.getAllQuotes().distinctUntilChanged().onEach { res ->
            res.onSuccess { quotes ->
                setState {
                    it.copy(
                        isLoading = false,
                        featuredQuotes = quotes.toMutableList(),
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

    fun onQuoteSwiped(quote: Quote) {
        setState {
            val newList = it.featuredQuotes.toMutableList().apply {
                removeFirstOrNull()
                add(quote) // Tambahkan ke akhir
            }
            it.copy(featuredQuotes = newList)
        }
    }
}