package org.bz.autoorganizer.ui.navigation

import androidx.annotation.StringRes
import org.bz.autoorganizer.R

enum class Screen(
    val route: String,
    @StringRes val screenTitle: Int
) {
    AUTO_STATE("auto_state", R.string.auto_state_screen),
    PROFILE("profile", R.string.profile_screen)
}