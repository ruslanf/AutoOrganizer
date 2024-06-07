package org.bz.autoorganizer.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import org.bz.autoorganizer.R

enum class NavigationSections(
    @StringRes val screenTitle: Int,
    val route: String,
    val icon: ImageVector
) {
    AUTO_STATE(R.string.auto_state_screen, "${AppDestinations.AUTO_STATE}", Icons.Outlined.Home),
    PROFILE(R.string.profile_screen, "${AppDestinations.PROFILE}/profile", Icons.Outlined.AccountCircle)
}