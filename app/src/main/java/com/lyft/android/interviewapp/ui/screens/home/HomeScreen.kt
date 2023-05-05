package com.lyft.android.interviewapp.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.*
import com.lyft.android.interviewapp.R
import com.lyft.android.interviewapp.ui.navigation.Routes
import com.lyft.android.interviewapp.ui.screens.search.SearchViewModel
import com.lyft.android.interviewapp.ui.screens.search.content.SearchScreen
import com.lyft.android.interviewapp.ui.theme.PrimaryColor
import com.lyft.android.interviewapp.ui.theme.TextColor

@Composable
fun HomeScreen(onEventClicked: (eventId: String) -> Unit) {
    val items = remember {
        listOf(
            HomeTabScreen.Search,
            HomeTabScreen.MyMissions,
            HomeTabScreen.Achievements,
            HomeTabScreen.Profile,
        )
    }

    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White
            ) {
                val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    val selected = remember(currentDestination, screen) {
                        currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    }
                    val tabContentColor by animateColorAsState(if (selected) PrimaryColor else TextColor)

                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(screen.iconResId),
                                contentDescription = null,
                                tint = tabContentColor
                            )
                        },
                        label = {
                            Text(
                                screen.tabName,
                                color = tabContentColor,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.W500
                            )
                        },
                        selected = selected,
                        onClick = {
                            bottomNavController.navigate(screen.route) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            bottomNavController,
            startDestination = HomeTabScreen.Search.route,
            Modifier.padding(innerPadding)
        ) {
            searchEventsGraph(onEventClicked)

            composable(HomeTabScreen.MyMissions.route) {
                Text(text = "My Missions")
            }
            composable(HomeTabScreen.Achievements.route) {
                Text(text = "Achievements")
            }
            composable(HomeTabScreen.Profile.route) {
                Text(text = "Profile")
            }
        }
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
private fun NavGraphBuilder.searchEventsGraph(
    onEventClicked: (eventId: String) -> Unit
) {
    navigation(route = HomeTabScreen.Search.route, startDestination = Routes.search) {
        composable(
            route = Routes.search
        ) {
            val state by hiltViewModel<SearchViewModel>().uiStateFlow.collectAsStateWithLifecycle()
            SearchScreen(
                state = state,
                onEventClicked = onEventClicked
            )
        }
    }
}

sealed class HomeTabScreen(
    val route: String,
    val tabName: String,
    @DrawableRes val iconResId: Int
) {
    object Search : HomeTabScreen(Routes.searchNavigation, "Пошук", R.drawable.ic_search)
    object MyMissions : HomeTabScreen(Routes.myMissions, "Мої місії", R.drawable.ic_my_missions)
    object Achievements :
        HomeTabScreen(Routes.achievements, "Досягнення", R.drawable.ic_achievements)
    object Profile : HomeTabScreen(Routes.profile, "Профіль", R.drawable.ic_profile)
}