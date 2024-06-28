package org.bz.autoorganizer.ui.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.bz.autoorganizer.root.NavigationTags

@Composable
fun BackButton(
    navController: NavController
) {
    Button(
        modifier = Modifier
            .padding(0.dp)
            .testTag(NavigationTags.BACK_BUTTON),
        onClick = {
            navController.navigateUp()
        },
        enabled = true,
        border = BorderStroke(
            width = 1.dp,
            brush = SolidColor(Color.Blue)
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = "Back Button"
        )
    }
}