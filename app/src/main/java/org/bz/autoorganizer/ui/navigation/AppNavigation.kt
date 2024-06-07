package org.bz.autoorganizer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.bz.autoorganizer.ui.autostate.AutoStateScreen
import org.bz.autoorganizer.ui.profile.ProfileScreen

@Composable
fun AppNavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: NavigationItem = NavigationItem.AutoState
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route
    ) {
        composable(Screen.AUTO_STATE.route) {
            AutoStateScreen()
        }
        composable(Screen.PROFILE.route) {
            ProfileScreen()
        }
    }
}