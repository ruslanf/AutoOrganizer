package org.bz.autoorganizer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.bz.autoorganizer.ui.autostate.AutoStateScreen
import org.bz.autoorganizer.ui.base.TopBarNavigation
import org.bz.autoorganizer.ui.profile.ProfileScreen
import org.bz.autoorganizer.ui.profile.auto.AddNewAutoScreen
import org.bz.autoorganizer.ui.profile.yandex.YandexOAuthScreen

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
        /*composable("") {
            TopBarNavigation {
                navController.popBackStack()
            }
        }*/
        composable(Screen.AUTO_STATE.route) {
            AutoStateScreen()
        }
        composable(Screen.PROFILE.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screen.ADD_NEW_AUTO.route) {
            AddNewAutoScreen()
        }
        composable(Screen.YANDEX_OAUTH.route) {
            YandexOAuthScreen()
        }
    }
}