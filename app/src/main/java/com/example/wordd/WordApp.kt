package com.example.wordd

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.wordd.data.DataStoreManager
import com.example.wordd.ui.EditWordScreen
import com.example.wordd.ui.WordsScreen
import com.example.wordd.ui.WordSettingsScreen
import com.example.wordd.ViewModel.WordsViewModel
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.material.icons.filled.Settings


enum class WordsScreen {
    Words,
    EditWord,
    Settings
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordsAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    showSettings: Boolean = false,
    onSettingsClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Буцах"
                    )
                }
            }
        },
        actions = {
            if (showSettings) {
                IconButton(onClick = onSettingsClick) {
                    Icon(
                        imageVector = Icons.Filled.Settings ,
                        contentDescription = "Тохиргоо"
                    )
                }
            }
        }
    )
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun WordsApp(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val application = context.applicationContext as android.app.Application
    val dataStoreManager = remember { DataStoreManager(context) }

    // Create ViewModel with factory
    val viewModel: WordsViewModel = viewModel(
        factory = WordsViewModel.Factory(application, dataStoreManager)
    )

    Scaffold(
        topBar = {
            val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route
            val isWordsScreen = currentScreen == WordsScreen.Words.name

            WordsAppBar(
                title = when {
                    currentScreen?.startsWith(WordsScreen.EditWord.name) == true -> "Үг засах"
                    currentScreen == WordsScreen.Settings.name -> "Тохиргоо"
                    else -> "Үг цээжлэх карт"
                },
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                showSettings = isWordsScreen,
                onSettingsClick = { navController.navigate(WordsScreen.Settings.name) }
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = WordsScreen.Words.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = WordsScreen.Words.name) {
                WordsScreen(
                    viewModel = viewModel,
                    onEditWordClick = { word ->
                        navController.navigate("${WordsScreen.EditWord.name}/${word.id}")
                    },
                    onAddWordClick = {
                        navController.navigate(WordsScreen.EditWord.name)
                    },

                )
            }

            composable(
                route = "${WordsScreen.EditWord.name}/{wordId}",
                arguments = listOf(navArgument("wordId") { type = NavType.IntType })
            ) {
                EditWordScreen(
                    viewModel = viewModel,
                    onSaveComplete = { navController.popBackStack() },
                    onCancel = { navController.navigateUp() },
                    navController = navController  // Pass the navController
                )
            }

            composable(route = WordsScreen.EditWord.name) {
                EditWordScreen(
                    viewModel = viewModel,
                    onSaveComplete = { navController.popBackStack() },
                    onCancel = { navController.navigateUp() },
                    navController = navController  // Pass the navController
                )
            }

            composable(route = WordsScreen.Settings.name) {
                WordSettingsScreen(
                    viewModel = viewModel,
                    onBack = { navController.navigateUp() }
                )
            }
        }
    }
}