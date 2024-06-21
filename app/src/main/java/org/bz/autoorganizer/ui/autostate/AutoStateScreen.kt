package org.bz.autoorganizer.ui.autostate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import org.bz.autoorganizer.ui.base.LoadingIndicator
import org.bz.autoorganizer.ui.theme.AutoOrganizerTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun AutoStateScreen() {
    val context = LocalContext.current
    val viewModel: AutoStateViewModel = koinViewModel()

    AutoOrganizerTheme {
        LoadingIndicator(progress = viewModel.progress.collectAsState(initial = 0).value)
    }
}