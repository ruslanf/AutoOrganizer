package org.bz.autoorganizer.ui.profile.auto

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.toSize
import org.bz.autoorganizer.R

@Composable
fun AutoModelField(
    isEnabled: Boolean = false,
    expandedModelsMenu: Boolean = false,
    selectedModel: String = "",
    selectedModelSize: Size = Size.Zero
) {
    var modelsMenu by remember(expandedModelsMenu) { mutableStateOf(expandedModelsMenu) }
    var model by remember(selectedModel) { mutableStateOf(selectedModel) }
    var modelSize by remember(selectedModelSize) { mutableStateOf(selectedModelSize) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { layoutCoordinates ->
                modelSize = layoutCoordinates.size.toSize()
            }
            .alpha(if (isEnabled) 1f else 0f),
        value = selectedModel,
        onValueChange = { model = it },
        label = {
            Text(stringResource(id = R.string.label_model_name))
        },
        trailingIcon = {
            IconButton(
                onClick = { modelsMenu = !modelsMenu }
            ) {
                Icon(
                    imageVector = if (modelsMenu)
                        Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = "Auto model"
                )
            }
        },
        enabled = isEnabled,
        singleLine = true
    )
}