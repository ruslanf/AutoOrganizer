package org.bz.autoorganizer.ui.profile.auto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.bz.autoorganizer.R
import org.bz.autoorganizer.root.NavigationTags
import org.bz.autoorganizer.ui.base.BackButton
import org.bz.autoorganizer.ui.base.LoadingIndicator
import org.bz.autoorganizer.ui.theme.AutoOrganizerTheme
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewAutoScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val viewModel = koinViewModel<AddNewAutoViewModel>()
    val scope = rememberCoroutineScope()

    // List of Auto manufacturers
    val manufacturers by viewModel.watchAutoManufacturers.collectAsState(initial = emptyList())
    // List of Auto models name
    val models by viewModel.watchAutoModels.collectAsState(initial = emptyList())

    val progress = remember { mutableIntStateOf(0) }
    val manufacturer = remember { mutableStateOf("") }

//    val modelYearDatePicker = rememberDatePickerState()
    val modelsDateOpenDialog = remember { mutableStateOf(true) }
    /*val confirmEnabled = remember {
        derivedStateOf { modelYearDatePicker.selectedDateMillis != null }
    }*/

    var expandedManufacturerMenu by remember { mutableStateOf(false) }
    var selectedManufacturer by remember { mutableStateOf("") }
    var selectedManufacturerSize by remember { mutableStateOf(Size.Zero) }

    var expandedModelsMenu by remember { mutableStateOf(false) }
    var selectedModel by remember { mutableStateOf("") }
    var selectedModelSize by remember { mutableStateOf(Size.Zero) }

    var isModelMenuEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = viewModel.progress) {
        scope.launch {
            launch {
                viewModel.progress.collect { progress.intValue = it }
            }
            launch {
                viewModel.watchAutoModels.collect { models ->
                    models.forEach { Timber.i("Model => ${it.name}") }
                }
            }
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(NavigationTags.ADD_NEW_AUTO_ROW),
                horizontalArrangement = Arrangement.Start
            ) {
                BackButton(navController = navController)
            }

            LoadingIndicator(
                progress = progress.intValue
            )

            AutoModelField(
                isEnabled = progress.intValue == 0,
                expandedMenu = expandedManufacturerMenu,
                selectedField = selectedManufacturer,
                fieldSize = selectedManufacturerSize,
                label = R.string.label_manufacturer_name,
                onExpandedMenu = {
                    expandedManufacturerMenu = it
                    scope.launch {
                        selectedModel = ""
                        launch { viewModel.doClearModels() }
                    }
                },
                onModelSelected = { selectedManufacturer = it },
                onFieldSize = { selectedManufacturerSize = it }
            )
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
                manufacturers.forEach { m ->
                    DropdownMenuItem(
                        text = { Text(text = m) },
                        onClick = {
                            manufacturer.value = m
                            selectedManufacturer = manufacturer.value
                            expandedManufacturerMenu = false
                            isModelMenuEnabled = true
                            scope.launch {
                                launch {
                                    viewModel.getModelByManufacturer(manufacturer.value)
                                }
                            }
                        }
                    )
                }
            }

            AutoModelField(
                isEnabled = isModelMenuEnabled,
                expandedMenu = expandedModelsMenu,
                selectedField = selectedModel,
                fieldSize = selectedModelSize,
                label = R.string.label_model_name,
                onExpandedMenu = { expandedModelsMenu = it },
                onModelSelected = { selectedModel = it },
                onFieldSize = { selectedModelSize = it }
            )
            DropdownMenu(
                expanded = expandedModelsMenu,
                onDismissRequest = { expandedModelsMenu = false },
                modifier = Modifier
                    .width(
                        with(LocalDensity.current) {
                            selectedModelSize.width.toDp()
                        }
                    )
            ) {
                models.forEach { model ->
                    DropdownMenuItem(
                        text = {
                            model.name?.also { Text(text = it) }
                        },
                        onClick = {
                            selectedModel = model.name ?: ""
                            expandedModelsMenu = false
                            isModelMenuEnabled = true
                        }
                    )
                }
            }

            // Auto model's year of production
            /*DatePicker(
                modifier = Modifier
                    .padding(16.dp),
                state = modelYearDatePicker,
                showModeToggle = false
            )
            Timber.i("0. Model's year of production => ${modelYearDatePicker.selectableDates}")
            Timber.i("1. Model's year of production => ${modelYearDatePicker.selectedDateMillis}")*/

            if (modelsDateOpenDialog.value) {
                val modelYearDatePicker = rememberDatePickerState()
                val confirmEnabled = remember {
                    derivedStateOf { modelYearDatePicker.selectedDateMillis != null }
                }

                DatePickerDialog(
                    onDismissRequest = {
                        Timber.i("onDismissRequest... ")
                        modelsDateOpenDialog.value = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                modelsDateOpenDialog.value = false
                                Timber.i("0. confirmButton... => ${modelYearDatePicker.selectedDateMillis}")
                            },
                            enabled = confirmEnabled.value
                        ) {
                            Text(text = "OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                modelsDateOpenDialog.value = false
                                Timber.i("1. confirmButton... => ${modelYearDatePicker.selectedDateMillis}")
                            },
                            enabled = confirmEnabled.value
                        ) {
                            Text(text = "Cancel")
                        }
                    }
                ) {
                    DatePicker(state = modelYearDatePicker)
                }
            }
        }
    }
}