
package com.example.wordd.data
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {
    val allWords: Flow<List<Word>> = wordDao.getAllWords()

    suspend fun insertWord(word: Word): Long {
        return wordDao.insertWord(word)
    }

    suspend fun updateWord(word: Word) {
        wordDao.updateWord(word)
    }

    suspend fun deleteWord(word: Word) {
        wordDao.deleteWord(word)
    }

    suspend fun getWordById(id: Int): Word? {
        return wordDao.getWordById(id)
    }
}