package com.lyft.android.interviewapp.ui.screens.home

import android.view.View
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lyft.android.interviewapp.R
import com.lyft.android.interviewapp.ui.navigation.Routes
import com.lyft.android.interviewapp.ui.screens.home.achievements.AchievementsScreen
import com.lyft.android.interviewapp.ui.screens.home.achievements.AchievementsViewModel
import com.lyft.android.interviewapp.ui.screens.home.mymissions.MyMissionsScreen
import com.lyft.android.interviewapp.ui.screens.home.mymissions.MyMissionsViewModel
import com.lyft.android.interviewapp.ui.screens.home.profile.ProfileScreen
import com.lyft.android.interviewapp.ui.screens.home.profile.ProfileViewModel
import com.lyft.android.interviewapp.ui.screens.home.search.SearchViewModel
import com.lyft.android.interviewapp.ui.screens.home.search.content.SearchScreen
import com.lyft.android.interviewapp.ui.screens.qrcode.QrCodeActivityResultContract
import com.lyft.android.interviewapp.ui.theme.PrimaryColor
import com.lyft.android.interviewapp.ui.theme.TextColor

@Composable
fun HomeScreen(
    onEventClicked: (eventId: String) -> Unit,
    onQrCodeScanned: (qrCodeContent: String?) -> Unit,
    onLoggedOut: () -> Unit,
    onEditProfileClicked: (userName: String) -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        (context as AppCompatActivity).run {
            window.navigationBarColor = getColor(android.R.color.white)
            window.statusBarColor = getColor(android.R.color.white)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    val homeViewModel = hiltViewModel<HomeScreenViewModel>()
    val qrCodeLauncher = rememberLauncherForActivityResult(
        contract = QrCodeActivityResultContract,
        onResult = onQrCodeScanned
    )

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
            BottomNavigation(backgroundColor = Color.White) {
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
            composable(
                route = HomeTabScreen.Search.route
            ) {
                val viewModel = hiltViewModel<SearchViewModel>()
                val state by viewModel.uiStateFlow.collectAsStateWithLifecycle()

                SearchScreen(
                    state = state,
                    onEventClicked = onEventClicked,
                    onFilterSelected = viewModel::onFilterSelected,
                    onQrCodeClicked = {
                        qrCodeLauncher.launch(Unit)
                    },
                    onCitySelected = viewModel::onCitySelected,
                    onRefresh = viewModel::refresh
                )
            }

            composable(HomeTabScreen.MyMissions.route) {
                val viewModel = hiltViewModel<MyMissionsViewModel>()
                val state by viewModel.uiStateFlow.collectAsStateWithLifecycle()

                MyMissionsScreen(
                    state = state,
                    onEventClicked = onEventClicked,
                    onFilterSelected = viewModel::onFilterSelected,
                    onQrCodeClicked = {
                        qrCodeLauncher.launch(Unit)
                    },
                    onRefresh = viewModel::refresh
                )
            }
            composable(HomeTabScreen.Achievements.route) {
                val viewModel = hiltViewModel<AchievementsViewModel>()
                AchievementsScreen(
                    viewModel.uiState,
                    onQrCodeClicked = { qrCodeLauncher.launch(Unit) },
                    onRefresh = viewModel::refresh
                )
            }
            composable(HomeTabScreen.Profile.route) {
                val viewModel = hiltViewModel<ProfileViewModel>()
                if (viewModel.uiState.isLoggedOut) {
                    LaunchedEffect(Unit) {
                        onLoggedOut()
                    }
                }
                ProfileScreen(
                    viewModel.uiState,
                    onQrCodeClicked = { qrCodeLauncher.launch(Unit) },
                    onEditProfileClicked = {
                        onEditProfileClicked(viewModel.uiState.name)
                    },
                    onAboutClicked = {},
                    onLogoutClicked = viewModel::logout,
                    onRefresh = viewModel::refresh
                )
            }
        }
    }
}

sealed class HomeTabScreen(
    val route: String,
    val tabName: String,
    @DrawableRes val iconResId: Int
) {
    object Search : HomeTabScreen(Routes.search, "Пошук", R.drawable.ic_search)
    object MyMissions : HomeTabScreen(Routes.myMissions, "Мої місії", R.drawable.ic_my_missions)
    object Achievements :
        HomeTabScreen(Routes.achievements, "Досягнення", R.drawable.ic_achievements)

    object Profile : HomeTabScreen(Routes.profile, "Профіль", R.drawable.ic_profile)
}