package org.bz.autoorganizer.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import org.bz.autoorganizer.R

sealed class NavigationItem(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector = Icons.Rounded.Warning
) {
    data object AutoState : NavigationItem(
        Screen.AUTO_STATE.route,
        Screen.AUTO_STATE.screenTitle,
        Icons.Rounded.Home
    )

    data object Profile : NavigationItem(
        Screen.PROFILE.route,
        Screen.PROFILE.screenTitle,
        Icons.Rounded.AccountCircle
    )

    data object AddNewAuto : NavigationItem(
        Screen.ADD_NEW_AUTO.route,
        Screen.ADD_NEW_AUTO.screenTitle,
        Icons.Rounded.Add
    )
}