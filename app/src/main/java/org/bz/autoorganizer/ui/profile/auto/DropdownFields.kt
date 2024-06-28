package org.bz.autoorganizer.ui.profile.auto

import androidx.annotation.StringRes
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
import okhttp3.internal.toHexString
import timber.log.Timber
import kotlin.math.exp

@Composable
fun AutoModelField(
    isEnabled: Boolean = false,
    expandedMenu: Boolean = false,
    selectedField: String = "",
    fieldSize: Size = Size.Zero,
    @StringRes label: Int,
    onExpandedMenu: (Boolean) -> Unit,
    onModelSelected: (String) -> Unit,
    onFieldSize: (Size) -> Unit
) {
    var menu by remember(expandedMenu) { mutableStateOf(expandedMenu) }
    var modelSize by remember(fieldSize) { mutableStateOf(fieldSize) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { layoutCoordinates ->
                modelSize = layoutCoordinates.size.toSize()
                onFieldSize(modelSize)
            }
            .alpha(if (isEnabled) 1f else 0f),
        value = selectedField,
        onValueChange = onModelSelected,
        label = {
            Text(stringResource(id = label))
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    menu = !menu
                    onExpandedMenu.invoke(menu)
                }
            ) {
                Icon(
                    imageVector = if (menu)
                        Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = "Auto model"
                )
            }
        },
        enabled = isEnabled,
        singleLine = true
    )
}