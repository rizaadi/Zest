package com.zephysus.zest.ui.screens.quotes

import androidx.lifecycle.viewModelScope
import com.zephysus.core.di.LocalRepository
import com.zephysus.core.repository.QuoteRepository
import com.zephysus.zest.ui.BaseViewModel
import com.zephysus.zest.utils.validator.QuoteValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddQuoteViewModel @Inject constructor(
    @LocalRepository private val localRepository: QuoteRepository,
) : BaseViewModel<AddQuotesState>(initialState = AddQuotesState()) {

    private var job: Job? = null

    fun setTitle(title: String) {
        setState {
            it.copy(title = title)
        }
        validateQuote()
    }

    fun setAuthor(author: String) {
        setState {
            it.copy(author = author)
        }
        validateQuote()
    }

    fun setFeatured(isFeatured: Boolean) {
        setState {
            it.copy(isFeatured = isFeatured)
        }
        validateQuote()
    }

    fun saveQuote() {
        job?.cancel()
        job = viewModelScope.launch {
            setState {
                it.copy(isLoading = true)
            }
            val result = localRepository.addQuote(
                title = state.value.title,
                author = state.value.author,
                isFeatured = state.value.isFeatured
            )
            result.onSuccess {
                setState {
                    it.copy(
                        isLoading = false,
                        added = true,
                    )
                }
            }.onFailure { message ->
                setState {
                    it.copy(
                        isLoading = false, added = false, errorMessage = message
                    )
                }
            }
        }
    }

    private fun validateQuote() {
        val isValid = QuoteValidator.isValidQuote(currentState.title, currentState.author)
        setState {
            it.copy(showSave = isValid)
        }
    }
}