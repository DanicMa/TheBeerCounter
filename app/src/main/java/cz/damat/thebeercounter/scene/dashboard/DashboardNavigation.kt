package cz.damat.thebeercounter.scene.dashboard

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cz.damat.thebeercounter.R
import cz.damat.thebeercounter.ui.theme.medium

/**
 * Created by Matej Danicek on 23.04.23.
 */
@Composable
fun DashboardNavigation() {
    //todo
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            NavigationHost(navController, it, DashboardNavigationItem.Counter.route)
        },
        bottomBar = {
            BottomBar(navController)
        }

    )
}

@Composable
private fun NavigationHost(navController: NavHostController, paddingValues: PaddingValues, startDestination: String) {
    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navController,
        startDestination = startDestination,
    ) {
        DashboardNavigationItem.values().forEach { navigationItem ->
            composable(navigationItem.route) {
                when (navigationItem) {
                    DashboardNavigationItem.Counter -> Text(text = navigationItem.route)
                    DashboardNavigationItem.History -> Text(text = navigationItem.route)
                    DashboardNavigationItem.More -> Text(text = navigationItem.route)
                }
            }
        }
    }
}


@Composable
fun BottomBar(navController: NavHostController) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .height(56.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        DashboardNavigationItem.values().forEach { navigationItem ->
            val isSelected = navigationItem.route == currentRoute
            val selectedColor = MaterialTheme.colors.primary
            val unselectedColor = MaterialTheme.colors.onSurface.medium

            BottomNavigationItem(
                selected = isSelected,
                onClick = { navController.navigate(navigationItem.route) },
                icon = {
                    Icon(
                        modifier = Modifier.padding(bottom = 2.dp),
                        imageVector = ImageVector.vectorResource(id = navigationItem.iconRes),
                        contentDescription = stringResource(id = navigationItem.titleRes)
                    )
                },
                selectedContentColor = selectedColor,
                unselectedContentColor = unselectedColor,
                label = {
                    Text(
                        text = stringResource(id = navigationItem.titleRes),
                    )
                    //todo - colored label of the selected item
                }
            )
        }
    }
}

private enum class DashboardNavigationItem(
    val route: String,
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int
) {
    Counter("counter", R.string.counter, R.drawable.ic_tally_24),
    History("history", R.string.history, R.drawable.ic_list_24),
    More("more", R.string.more, R.drawable.ic_more_horizontal_24),
}