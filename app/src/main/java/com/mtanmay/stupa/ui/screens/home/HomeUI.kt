package com.mtanmay.stupa.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mtanmay.stupa.navigation.HomeNavGraph
import com.mtanmay.stupa.ui.theme.primary
import com.mtanmay.stupa.utils.PreferencesManager
import com.mtanmay.stupa.viewmodels.HomeViewModel

@Composable
fun HomeUI(
    modifier: Modifier = Modifier,
    tabList: List<TabNavigationItem> = emptyList(),
    navController: NavHostController = rememberNavController(),
    onLogOut: () -> Unit = {}
) {
    var selectedTabIdx by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier,
                containerColor = primary
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                tabList.forEachIndexed { _, tab ->
                    NavigationBarItem(
                        label = {
                            Text(text = tab.label)
                        },
                        alwaysShowLabel = false,
                        selected = currentRoute == tab.route,
                        onClick = {
                            navController.navigate(
                                tab.route
                            ) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    ) {
        val context = LocalContext.current

        val viewmodel: HomeViewModel = hiltViewModel()
        val prefManager = PreferencesManager(context)

        Column(
            modifier = modifier.padding(it)
        ) {
            HomeNavGraph(
                viewmodel = viewmodel,
                preferenceManager = prefManager,
                navController = navController,
                onLogOut = onLogOut
            )
        }
    }
}

@Preview
@Composable
private fun HomeUIPreview() {
//    HomeUI(
//        navController = rememberNavController(),
//        authNavGraphId = androidx.navigation.NavGraph(null)
//    )
}