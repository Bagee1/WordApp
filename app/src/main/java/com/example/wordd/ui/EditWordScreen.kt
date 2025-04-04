package com.example.wordd.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wordd.data.Word
import com.example.wordd.ViewModel.WordsViewModel

@Composable
fun EditWordScreen(
    viewModel: WordsViewModel,
    onSaveComplete: () -> Unit,
    onCancel: () -> Unit,
    navController: NavController? = null
) {
    val wordId = navController?.currentBackStackEntry?.arguments?.getInt("wordId", 0) ?: 0

    val wordToEdit = remember(wordId) {
        if (wordId > 0) {
            viewModel.uiState.value.words.find { it.id == wordId }
        } else null
    }

    var foreignWord by remember { mutableStateOf(wordToEdit?.foreignWord ?: "") }
    var nativeWord by remember { mutableStateOf(wordToEdit?.nativeWord ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (wordToEdit == null) "Шинэ үг нэмэх" else "Үг засах",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = foreignWord,
            onValueChange = { foreignWord = it },
            label = { Text("Гадаад үг") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nativeWord,
            onValueChange = { nativeWord = it },
            label = { Text("Монгол үг") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onCancel) {
                Text("Цуцлах")
            }

            Button(
                onClick = {
                    if (foreignWord.isNotBlank() && nativeWord.isNotBlank()) {
                        val word = Word(
                            id = wordToEdit?.id ?: 0,
                            foreignWord = foreignWord,
                            nativeWord = nativeWord
                        )

                        if (wordToEdit == null) {
                            viewModel.addWord(word)
                        } else {
                            viewModel.updateWord(word)
                        }

                        onSaveComplete()
                    }
                }
            ) {
                Text(if (wordToEdit == null) "Нэмэх" else "Хадгалах")
            }
        }
    }
}