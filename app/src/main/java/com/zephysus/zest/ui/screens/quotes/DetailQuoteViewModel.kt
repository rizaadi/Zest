package com.zephysus.zest.ui.screens.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zephysus.core.di.LocalRepository
import com.zephysus.core.model.Quote
import com.zephysus.core.repository.QuoteRepository
import com.zephysus.zest.ui.BaseViewModel
import com.zephysus.zest.utils.validator.QuoteValidator
import dagger.Module
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class DetailQuoteViewModel @AssistedInject constructor(
    @LocalRepository private val localRepository: QuoteRepository,
    @Assisted private val quoteId: String,
) : BaseViewModel<DetailQuoteState>(initialState = DetailQuoteState()) {

    private var job: Job? = null
    private lateinit var currentQuote: Quote

    init {
        getQuote()
    }

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

    fun getQuote() {
        job?.cancel()
        job = viewModelScope.launch {
            setState {
                it.copy(isLoading = true)
            }
            val result = localRepository.getQuoteById(quoteId).firstOrNull()
            if (result != null) {
                currentQuote = result
                setState {
                    it.copy(
                        isLoading = false,
                        title = result.title,
                        author = result.author,
                    )
                }
            } else {
                setState {
                    it.copy(
                        isLoading = false, finished = true, errorMessage = "Quote not found"
                    )
                }
            }
        }
    }


    fun save() {
        val title = currentState.title?.trim() ?: return
        val author = currentState.author?.trim() ?: return

        job?.cancel()
        job = viewModelScope.launch {
            setState { it.copy(isLoading = true) }

            val response = localRepository.updateQuote(quoteId, title, author)

            setState { it.copy(isLoading = false) }

            response.onSuccess { quoteId ->
//                if (QuoteRepository.isTemporaryQuote(quoteId)) {
//                    scheduleNoteCreate(quoteId)
//                } else {
//                    scheduleNoteUpdate(quoteId)
//                }
                setState { it.copy(finished = true) }
            }.onFailure { message ->
                setState { it.copy(errorMessage = message) }
            }
        }
    }

    private fun validateQuote() {
        val oldTitle = currentQuote.title
        val oldNote = currentQuote.author

        val title = currentState.title
        val author = currentState.author

        val isValid = title != null && author != null && QuoteValidator.isValidQuote(title, author)

        val areOldAndUpdatedQuoteSame = oldTitle == title?.trim() && oldNote == author?.trim()

        setState { it.copy(showSave = isValid && !areOldAndUpdatedQuoteSame) }

    }

    @AssistedFactory
    interface Factory {
        fun create(quoteId: String): DetailQuoteViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            quoteId: String,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(quoteId) as T
            }
        }
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
interface AssistedInjectModule