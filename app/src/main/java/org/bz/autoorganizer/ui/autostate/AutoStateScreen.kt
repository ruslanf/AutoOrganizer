package org.bz.autoorganizer.ui.autostate

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun AutoStateScreen() {
    val viewModel: AutoStateViewModel = koinViewModel()
}