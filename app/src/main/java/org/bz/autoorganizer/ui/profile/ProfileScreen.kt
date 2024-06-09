package org.bz.autoorganizer.ui.profile

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import org.bz.autoorganizer.ui.base.LoadingIndicator
import org.bz.autoorganizer.ui.theme.AutoOrganizerTheme
import org.koin.androidx.compose.koinViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val viewModel: ProfileViewModel = koinViewModel()
    val scope = rememberCoroutineScope()

    var progress = 0

    /*scope.launch {
        viewModel.progress.collect {
            Timber.v("progress => $it")
            progress = it
        }
    }*/

    AutoOrganizerTheme {
        LoadingIndicator(viewModel.progress)


        var expandedAutoMenu by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopCenter)
        ) {
            IconButton(onClick = { expandedAutoMenu = !expandedAutoMenu }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Auto"
                )
            }
            DropdownMenu(
                expanded = expandedAutoMenu,
                onDismissRequest = { expandedAutoMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Auto model") },
                    onClick = { Toast.makeText(context, "Load", Toast.LENGTH_SHORT).show() }
                )
            }
        }
    }
}