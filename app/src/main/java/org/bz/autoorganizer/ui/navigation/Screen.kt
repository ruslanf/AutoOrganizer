package org.bz.autoorganizer.ui.navigation

import androidx.annotation.StringRes
import org.bz.autoorganizer.R

enum class Screen(
    val route: String,
    @StringRes val screenTitle: Int
) {
    AUTO_STATE("auto_state", R.string.auto_state_screen),
    PROFILE("profile", R.string.profile_screen),
    ADD_NEW_AUTO("add_new_auto", R.string.add_new_auto_screen),
    YANDEX_OAUTH("yandex_oauth", R.string.yandex_oauth_screen)
}