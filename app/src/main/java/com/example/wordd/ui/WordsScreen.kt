package com.example.wordd.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.wordd.data.Word
import com.example.wordd.ViewModel.WordsViewModel
import androidx.compose.foundation.gestures.detectTapGestures
import android.content.res.Configuration
import androidx.compose.ui.platform.LocalConfiguration
@Composable
fun WordsScreen(
    viewModel: WordsViewModel,
    onEditWordClick: (Word) -> Unit,
    onAddWordClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        // Landscape layout
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Left side - Word card
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(end = 8.dp)
            ) {
                if (uiState.words.isNotEmpty()) {
                    val currentWord = uiState.words[uiState.currentIndex]

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = {
                                        onEditWordClick(currentWord)
                                    }
                                )
                            },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            when (uiState.displayMode) {
                                0 -> {
                                    Text(
                                        text = "Гадаад үг: ${currentWord.foreignWord}",
                                        style = MaterialTheme.typography.headlineMedium
                                    )
                                    Text(
                                        text = "Монгол: ${currentWord.nativeWord}",
                                        style = MaterialTheme.typography.headlineMedium
                                    )
                                }
                                1 -> {
                                    Text(
                                        text = "Гадаад үг: ${currentWord.foreignWord}",
                                        style = MaterialTheme.typography.headlineMedium
                                    )
                                }
                                2 -> {
                                    Text(
                                        text = "Монгол үг: ${currentWord.nativeWord}",
                                        style = MaterialTheme.typography.headlineMedium
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Text("Үг олдсонгүй", style = MaterialTheme.typography.bodyLarge)
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (uiState.words.isNotEmpty()) {
                    val currentWord = uiState.words[uiState.currentIndex]

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { viewModel.previousWord() },
                            enabled = uiState.currentIndex > 0
                        ) {
                            Text("Өмнөх")
                        }
                        Button(onClick = { viewModel.deleteWord(currentWord) }) {
                            Text("Устгах")
                        }
                        Button(
                            onClick = { viewModel.nextWord() },
                            enabled = uiState.currentIndex < uiState.words.size - 1
                        ) {
                            Text("Дараах")
                        }
                    }

                    Button(
                        onClick = { onEditWordClick(currentWord) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Үг засах")
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = onAddWordClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Шинэ үг нэмэх")
                }
            }
        }
    } else {
        // Portrait layout (your original code)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // Your existing portrait layout code...
            if (uiState.words.isNotEmpty()) {
                val currentWord = uiState.words[uiState.currentIndex]

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    onEditWordClick(currentWord)
                                }
                            )
                        },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        when (uiState.displayMode) {
                            0 -> {
                                Text(
                                    text = "Гадаад үг: ${currentWord.foreignWord}",
                                    style = MaterialTheme.typography.headlineMedium
                                )
                                Text(
                                    text = "Монгол: ${currentWord.nativeWord}",
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                            1 -> {
                                Text(
                                    text = "Гадаад үг: ${currentWord.foreignWord}",
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                            2 -> {
                                Text(
                                    text = "Монгол үг: ${currentWord.nativeWord}",
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { viewModel.previousWord() },
                        enabled = uiState.currentIndex > 0
                    ) {
                        Text("Өмнөх")
                    }
                    Button(onClick = { viewModel.deleteWord(currentWord) }) {
                        Text("Устгах")
                    }
                    Button(
                        onClick = { viewModel.nextWord() },
                        enabled = uiState.currentIndex < uiState.words.size - 1
                    ) {
                        Text("Дараах")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onEditWordClick(currentWord) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Үг засах")
                }
            } else {
                Text("Үг олдсонгүй", style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onAddWordClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Шинэ үг нэмэх")
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}