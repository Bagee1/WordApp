package com.example.wordd.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wordd.ViewModel.WordsUiState
import com.example.wordd.data.DataStoreManager
import com.example.wordd.data.DatabaseProvider
import com.example.wordd.data.Word
import com.example.wordd.data.WordRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WordsViewModel(
    application: Application,
    private val dataStoreManager: DataStoreManager
) : AndroidViewModel(application) {

    private val repository: WordRepository = DatabaseProvider.provideWordRepository(application)

    private val _uiState = MutableStateFlow(WordsUiState())
    val uiState: StateFlow<WordsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.allWords.collect { wordsList ->
                _uiState.update { it.copy(words = wordsList) }
            }
        }

        viewModelScope.launch {
            dataStoreManager.displayModeFlow.collect { mode ->
                _uiState.update { it.copy(displayMode = mode) }
            }
        }
    }

    fun addWord(newWord: Word) {
        viewModelScope.launch {
            repository.insertWord(newWord)
        }
    }

    fun updateWord(word: Word) {
        viewModelScope.launch {
            repository.updateWord(word)
        }
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch {
            repository.deleteWord(word)
        }
    }

    fun nextWord() {
        _uiState.update {
            val nextIndex = it.currentIndex + 1
            if (nextIndex < it.words.size) it.copy(currentIndex = nextIndex) else it
        }
    }

    fun previousWord() {
        _uiState.update {
            val prevIndex = it.currentIndex - 1
            if (prevIndex >= 0) it.copy(currentIndex = prevIndex) else it
        }
    }

    fun setDisplayMode(mode: Int) {
        viewModelScope.launch {
            dataStoreManager.setDisplayMode(mode)
        }
    }

    class Factory(
        private val application: Application,
        private val dataStoreManager: DataStoreManager
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WordsViewModel::class.java)) {
                return WordsViewModel(application, dataStoreManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}