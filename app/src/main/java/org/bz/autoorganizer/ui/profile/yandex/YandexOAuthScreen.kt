package org.bz.autoorganizer.ui.profile.yandex

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthResult
import org.bz.autoorganizer.R
import org.bz.autoorganizer.root.NavigationTags
import org.bz.autoorganizer.ui.theme.AutoOrganizerTheme
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun YandexOAuthScreen() {

    val context = LocalContext.current
    val viewModel = koinViewModel<YandexOAuthViewModel>()

//    val result = remember { mutableStateOf<YandexAuthResult>() }
    val launcher = rememberLauncherForActivityResult(
        contract = viewModel.sdk.contract
    ) { yandexAuthResult ->
        viewModel.handleResult(yandexAuthResult)
//        result.value = yandexAuthResult
    }

    AutoOrganizerTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .testTag(NavigationTags.YANDEX_OAUTH_SCREEN),
            verticalArrangement = Arrangement.Center
        ) {
            ButtonYandexDialog(
                launcher = launcher,
                viewModel = viewModel
            )
            Text(text = "Yandex token => ${viewModel.watchYandexAuthState.collectAsState(initial = YandexAuthState.NeutralState)}")
        }
    }
}