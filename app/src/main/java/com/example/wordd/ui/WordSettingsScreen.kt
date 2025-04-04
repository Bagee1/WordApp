package com.example.wordd.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wordd.ViewModel.WordsViewModel

@Composable
fun WordSettingsScreen(viewModel: WordsViewModel, onBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Тохиргоо", style = MaterialTheme.typography.headlineMedium)

        Text("Үг харуулах горим:", style = MaterialTheme.typography.bodyLarge)

        RadioButtonWithLabel(
            label = "Монгол + Гадаад (Анхны тохиргоо)",
            selected = uiState.displayMode == 0,
            onSelect = { viewModel.setDisplayMode(0) }
        )

        RadioButtonWithLabel(
            label = "Зөвхөн Гадаад үг",
            selected = uiState.displayMode == 1,
            onSelect = { viewModel.setDisplayMode(1) }
        )

        RadioButtonWithLabel(
            label = "Зөвхөн Монгол үг",
            selected = uiState.displayMode == 2,
            onSelect = { viewModel.setDisplayMode(2) }
        )

        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Буцах")
        }
    }
}

@Composable
fun RadioButtonWithLabel(label: String, selected: Boolean, onSelect: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = onSelect)
        Spacer(modifier = Modifier.width(8.dp))
        Text(label)
    }
}
