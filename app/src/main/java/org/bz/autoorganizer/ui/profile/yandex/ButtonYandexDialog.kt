package org.bz.autoorganizer.ui.profile.yandex

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yandex.authsdk.YandexAuthLoginOptions
import org.bz.autoorganizer.R
import org.bz.autoorganizer.root.NavigationTags

@Composable
fun ButtonYandexDialog(
    launcher: ActivityResultLauncher<YandexAuthLoginOptions>,
    viewModel: YandexOAuthViewModel
) {
    Button(
        modifier = Modifier
            .padding(8.dp)
            .testTag(NavigationTags.YANDEX_OAUTH_BUTTON),
        onClick = {
            launcher.launch(viewModel.loginOptions)
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