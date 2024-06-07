package org.bz.autoorganizer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationDefaults
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.bz.autoorganizer.ui.navigation.AppNavigationHost
import org.bz.autoorganizer.ui.navigation.NavigationItem
import org.bz.autoorganizer.ui.theme.AutoOrganizerTheme

class MainActivity : ComponentActivity() {

    private val items = listOf(
        NavigationItem.AutoState,
        NavigationItem.Profile
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App(items)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AutoOrganizerTheme {
        Greeting("Android")
    }
}

@Composable
fun App(navigationItems: List<NavigationItem>) {

    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }

    AutoOrganizerTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavigation(windowInsets = BottomNavigationDefaults.windowInsets) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    navigationItems.forEachIndexed { index, screen ->
                        BottomNavigationItem(
                            icon = { Icon(screen.icon, contentDescription = "") },
                            label = { Text(text = stringResource(id = screen.resourceId)) },
                            selected = currentDestination?.hierarchy?.any { destination ->
                                destination.route == screen.route
                            } == true, /*selectedItem == index,*/
                            onClick = {
                                navController.navigate(screen.route) {

                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            /*Greeting(
                name = "Android",
                modifier = Modifier.padding(innerPadding)
            )*/
            AppNavigationHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = NavigationItem.AutoState
            )
        }
    }
}