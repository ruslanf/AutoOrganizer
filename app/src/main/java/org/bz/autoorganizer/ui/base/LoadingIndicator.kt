package org.bz.autoorganizer.ui.base

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import kotlinx.coroutines.flow.Flow

@Composable
fun LoadingIndicator(
    progress: Flow<Int>
) {
    val count by progress.collectAsState(initial = 0)

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (topText) = createRefs()
        if (count > 0) CircularProgressIndicator(
            modifier = Modifier
                .width(32.dp)
                .constrainAs(topText) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                },
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
fun LoadingIndicator(
    progress: Flow<Int>,
    modifier: Modifier = Modifier
) {
    val count by progress.collectAsState(initial = 0)

    if (count > 0) CircularProgressIndicator(
        modifier = modifier.width(32.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surface
    )
}