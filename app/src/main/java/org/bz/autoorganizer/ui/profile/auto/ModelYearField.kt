package org.bz.autoorganizer.ui.profile.auto

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModelYearField(

) {
    val modelsDateOpenDialog = remember { mutableStateOf(true) }

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