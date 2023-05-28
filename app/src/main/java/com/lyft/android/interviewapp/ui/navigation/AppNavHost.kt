package com.lyft.android.interviewapp.ui.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lyft.android.interviewapp.ui.screens.details.EventDetailsScreen
import com.lyft.android.interviewapp.ui.screens.details.EventDetailsViewModel
import com.lyft.android.interviewapp.ui.screens.home.HomeScreen
import com.lyft.android.interviewapp.ui.screens.login.LoginScreen
import com.lyft.android.interviewapp.ui.screens.login.LoginViewModel
import com.lyft.android.interviewapp.ui.screens.onboarding.OnBoardingScreen
import com.lyft.android.interviewapp.ui.screens.onboarding.OnBoardingViewModel
import com.lyft.android.interviewapp.ui.screens.qrcode.QrCodeActivityResultContract

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.login
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.fillMaxSize()
    ) {
        composable(
            route = Routes.login
        ) {
            val viewModel: LoginViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            LoginScreen(
                state = state,
                onAuthResult = viewModel::handleGoogleAccountTask,
                onLoginCompleted = { isNewUser, userName ->
                    if (isNewUser) {
                        navController.navigate(Navigation.onBoardingDestination(userName))
                    } else {
                        navController.navigate(Routes.home)
                    }
                }
            )
        }

        composable(
            route = Routes.onBoarding,
            arguments = listOf(navArgument(NavArguments.userName) { type = NavType.StringType })
        ) {
            val viewModel: OnBoardingViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            OnBoardingScreen(
                state = state,
                onNameChanged = viewModel::onNameChanged,
                onCitySelected = viewModel::onCitySelected,
                onCreateAccountClicked = viewModel::createAccount,
                onAccountCreated = { navController.navigate(Routes.home) },
                onCloseClicked = navController::popBackStack
            )
        }

        composable(Routes.home) {
            HomeScreen(
                onEventClicked = { eventId ->
                    navController.navigate(Navigation.eventDetailsDestination(eventId))
                }
            )
        }

        composable(
            route = Routes.eventDetails,
            arguments = listOf(navArgument(NavArguments.eventId) { type = NavType.StringType })
        ) {
            val viewModel: EventDetailsViewModel = hiltViewModel()
            val state by viewModel.uiStateFlow.collectAsStateWithLifecycle()

            val qrCodeLauncher = rememberLauncherForActivityResult(
                contract = QrCodeActivityResultContract,
                onResult = viewModel::onQrCodeScanned
            )

            EventDetailsScreen(
                state = state,
                onRegisterClicked = viewModel::registerToEvent,
                onGoBackClicked = navController::popBackStack,
                onQrCodeClicked = {
                    qrCodeLauncher.launch(Unit)
                },
                callOrganizerClicked = {},
            )
        }
    }
}