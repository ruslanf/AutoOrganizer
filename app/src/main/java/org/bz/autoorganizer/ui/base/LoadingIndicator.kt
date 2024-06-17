package org.bz.autoorganizer.ui.base

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Composable function for showing circular progressbar
 * when progress value greater than 0
 */
@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    progress: Int = 0
) {
    if (progress > 0) CircularProgressIndicator(
        modifier = modifier
            .width(32.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surface
    )
}