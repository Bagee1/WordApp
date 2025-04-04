package com.example.wordd.ViewModel

import com.example.wordd.data.Word

data class WordsUiState(
    val words: List<Word> = emptyList(),
    val currentIndex: Int = 0,
    val displayMode: Int = 0  // 0: Бүгд, 1: Зөвхөн гадаад, 2: Зөвхөн монгол
)
