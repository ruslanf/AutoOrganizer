package org.bz.autoorganizer.ui.profile.auto

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.launch
import org.bz.autoorganizer.R
import org.bz.autoorganizer.root.NavigationTags
import org.bz.autoorganizer.ui.base.LoadingIndicator
import org.bz.autoorganizer.ui.theme.AutoOrganizerTheme
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun AddNewAutoScreen() {
    val context = LocalContext.current
    val viewModel = koinViewModel<AddNewAutoViewModel>()
    val scope = rememberCoroutineScope()

    // List of Auto manufacturers
    val manufacturers by viewModel.autoManufacturers.collectAsState(initial = emptyList())
    // List of Auto models name
    val models = mutableListOf<String>()

    var expandedManufacturerMenu by remember { mutableStateOf(false) }
    var selectedManufacturer by remember { mutableStateOf("") }
    var selectedManufacturerSize by remember { mutableStateOf(Size.Zero) }

    var expandedModelsMenu by remember { mutableStateOf(false) }
    var selectedModel by remember { mutableStateOf("") }
    var selectedModelSize by remember { mutableStateOf(Size.Zero) }

    var isModelMenuEnabled by remember { mutableStateOf(false) }

    val progress = remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            viewModel.progress.collect { progress.intValue = it }
        }
    }

    AutoOrganizerTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .testTag(NavigationTags.ADD_NEW_AUTO_SCREEN),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingIndicator(
                progress = progress.intValue
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { layoutCoordinates ->
                        selectedManufacturerSize = layoutCoordinates.size.toSize()
                    }
                    .alpha(if (progress.intValue > 0) 0f else 1f),
                value = selectedManufacturer,
                onValueChange = { selectedManufacturer = it },
                label = {
                    Text(stringResource(id = R.string.label_manufacturer_name))
                },
                trailingIcon = {
                    IconButton(
                        onClick = { expandedManufacturerMenu = !expandedManufacturerMenu }
                    ) {
                        Icon(
                            imageVector = if (expandedManufacturerMenu)
                                Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            contentDescription = "Auto"
                        )
                    }
                },
                singleLine = true
            )
            AutoModelField(isEnabled = isModelMenuEnabled)
        }

        DropdownMenu(
            expanded = expandedManufacturerMenu,
            onDismissRequest = { expandedManufacturerMenu = false },
            modifier = Modifier
                .width(
                    with(LocalDensity.current) {
                        selectedManufacturerSize.width.toDp()
                    }
                )
        ) {
            manufacturers.forEach { manufacturer ->
                DropdownMenuItem(
                    text = { Text(text = manufacturer) },
                    onClick = {
                        selectedManufacturer = manufacturer
                        expandedManufacturerMenu = false
                        isModelMenuEnabled = true
                        //TODO remove later below line
                        Toast.makeText(
                            context,
                            "Auto manufacturer's name => $manufacturer",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }
}