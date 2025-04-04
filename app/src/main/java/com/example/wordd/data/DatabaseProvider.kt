
package com.example.wordd.data

import android.content.Context

object DatabaseProvider {
    private var wordRepository: WordRepository? = null

    fun provideWordRepository(context: Context): WordRepository {
        return wordRepository ?: kotlin.run {
            val database = AppDatabase.getDatabase(context)
            val repo = WordRepository(database.wordDao())
            wordRepository = repo
            repo
        }
    }
}