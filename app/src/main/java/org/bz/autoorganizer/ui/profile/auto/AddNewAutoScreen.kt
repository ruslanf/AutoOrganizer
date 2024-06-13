package org.bz.autoorganizer.ui.profile.auto

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import org.bz.autoorganizer.root.NavigationTags
import org.bz.autoorganizer.ui.theme.AutoOrganizerTheme

@Preview
@Composable
fun AddNewAutoScreen() {
    val context = LocalContext.current

    AutoOrganizerTheme {
        Column(
            modifier = Modifier
                .testTag(NavigationTags.ADD_NEW_AUTO_SCREEN)
        ) {

        }
        /*ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (topText, centerText) = createRefs()

            *//*LoadingIndicator(
                progress = ,
                modifier = Modifier
                    .width(32.dp)
                    .constrainAs(topText) {
                        centerHorizontallyTo(parent)
                        centerVerticallyTo(parent)
                    }
            )*//*
        }*/


    }
}