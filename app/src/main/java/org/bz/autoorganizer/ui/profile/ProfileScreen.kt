package org.bz.autoorganizer.ui.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import org.bz.autoorganizer.R
import org.bz.autoorganizer.root.NavigationTags
import org.bz.autoorganizer.ui.navigation.Screen
import org.bz.autoorganizer.ui.theme.AutoOrganizerTheme
import org.koin.androidx.compose.koinViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProfileScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val viewModel: ProfileViewModel = koinViewModel()
    val scope = rememberCoroutineScope()

    AutoOrganizerTheme {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .testTag(NavigationTags.PROFILE_SCREEN),
            horizontalArrangement = Arrangement.Center
        ) {
            ButtonOpenAddNewAutoScreen(navController = navController)

            ButtonYandexAuthScreen(navController = navController)
        }
        /*ConstraintLayout(
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

            *//**
             * Select auto manufacturer
             *//*
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

            }
        }*/
    }
}

@Composable
fun ButtonOpenAddNewAutoScreen(
    navController: NavController
) {
    Button(
        modifier = Modifier
            .padding(8.dp)
            .testTag(NavigationTags.ADD_NEW_AUTO_BUTTON),
        onClick = {
            navController.navigate(Screen.ADD_NEW_AUTO.route)
        },
        enabled = true,
        border = BorderStroke(
            width = 1.dp,
            brush = SolidColor(Color.Blue)
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text = stringResource(id = R.string.button_add_new_auto_screen))
    }
}

@Composable
fun ButtonYandexAuthScreen(
    navController: NavController
) {
    Button(
        modifier = Modifier
            .padding(8.dp)
            .testTag(NavigationTags.YANDEX_OAUTH_BUTTON),
        onClick = {
            navController.navigate(Screen.YANDEX_OAUTH.route)
        },
        enabled = true,
        border = BorderStroke(
            width = 1.dp,
            brush = SolidColor(Color.Blue)
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text = stringResource(id = R.string.button_yandex_oauth_screen))
    }
}