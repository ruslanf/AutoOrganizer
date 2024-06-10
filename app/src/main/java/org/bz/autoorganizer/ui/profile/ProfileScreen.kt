package org.bz.autoorganizer.ui.profile

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.constraintlayout.compose.ConstraintLayout
import kotlinx.coroutines.launch
import org.bz.autoorganizer.ui.base.LoadingIndicator
import org.bz.autoorganizer.ui.theme.AutoOrganizerTheme
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val viewModel: ProfileViewModel = koinViewModel()
    val scope = rememberCoroutineScope()

    // List of Auto manufacturers
    val manufacturers = mutableListOf<String>()
    // List of Auto models name
    val models = mutableListOf<String>()

    var expandedManufacturerMenu by remember { mutableStateOf(false) }
    var selectedManufacturer by remember { mutableStateOf("") }
    var selectedManufacturerSize by remember { mutableStateOf(Size.Zero) }

    var expandedModelsMenu by remember { mutableStateOf(false) }
    var selectedModel by remember { mutableStateOf("") }
    var selectedModelSize by remember { mutableStateOf(Size.Zero) }

    scope.launch {
        viewModel.autoManufacturers.collect {
            Timber.v("manufacturers => $it")
            manufacturers.addAll(it)
        }
    }

    AutoOrganizerTheme {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (topText, centerText) = createRefs()

            LoadingIndicator(
                progress = viewModel.progress,
                modifier = Modifier
                    .width(32.dp)
                    .constrainAs(topText) {
                        centerHorizontallyTo(parent)
                        centerVerticallyTo(parent)
                    }
            )

            /**
             * Select auto manufacturer
             */
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .constrainAs(topText) {
                        top.linkTo(
                            parent.top,
                            margin = 32.dp
                        )
                        start.linkTo(
                            parent.start,
                            margin = 32.dp
                        )
                        end.linkTo(
                            parent.end,
                            margin = 32.dp
                        )
                    }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { layoutCoordinates ->
                            selectedManufacturerSize = layoutCoordinates.size.toSize()
                        },
                    value = selectedManufacturer,
                    onValueChange = { selectedManufacturer = it },
                    label = { Text(selectedManufacturer) },
                    trailingIcon = {
                        IconButton(onClick = { expandedManufacturerMenu = !expandedManufacturerMenu }) {
                            Icon(
                                imageVector = if (expandedManufacturerMenu)
                                    Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                                contentDescription = "Auto"
                            )
                        }
                    }
                )
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
                            Toast.makeText(
                                context,
                                "Auto manufacturer's name => $manufacturer",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    ) /*{
                        Text(text = manufacturer)
                    }*/
                }

            }
        }
    }
}

/*Box(
                    modifier = Modifier
                        .size(width = 100.dp, height = 300.dp)
                ) {

                    LazyColumn {

                    }
                }*/