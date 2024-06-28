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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.bz.autoorganizer.root.NavigationTags
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navigationItems: List<NavigationItem>) {

    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }

    AutoOrganizerTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            /*topBar = {
                TopAppBar(
                    title = {  },
//                    colors = TopAppBarColors(Color.Black),
                    navigationIcon = {
                        if (navController.previousBackStackEntry != null) IconButton(
                            onClick = { navController.navigateUp() }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                tint = Color.Black,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            },*/
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
            AppNavigationHost(
                modifier = Modifier
                    .padding(innerPadding)
                    .testTag(NavigationTags.APP_NAVIGATION_HOST),
                navController = navController,
                startDestination = NavigationItem.AutoState
            )
        }
    }
}