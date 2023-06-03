package com.lyft.android.interviewapp.ui.navigation

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lyft.android.interviewapp.R
import com.lyft.android.interviewapp.ui.screens.details.EventDetailsScreen
import com.lyft.android.interviewapp.ui.screens.details.EventDetailsViewModel
import com.lyft.android.interviewapp.ui.screens.home.AppNavHostViewModel
import com.lyft.android.interviewapp.ui.screens.home.HomeScreen
import com.lyft.android.interviewapp.ui.screens.login.LoginScreen
import com.lyft.android.interviewapp.ui.screens.login.LoginViewModel
import com.lyft.android.interviewapp.ui.screens.onboarding.DisplayMode
import com.lyft.android.interviewapp.ui.screens.onboarding.OnBoardingScreen
import com.lyft.android.interviewapp.ui.screens.onboarding.OnBoardingViewModel
import com.lyft.android.interviewapp.ui.screens.qrcode.QrCodeActivityResultContract


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.login
) {
    val hostViewModel = hiltViewModel<AppNavHostViewModel>()

    val scaffoldState = rememberScaffoldState()

    val qrCodeLauncher = rememberLauncherForActivityResult(
        contract = QrCodeActivityResultContract,
        onResult = hostViewModel::onQrCodeScanned
    )

    val hostState = hostViewModel.uiState

    LaunchedEffect(hostState.message) {
        if (hostState.message != null) {
            Log.d("SUCCESS", "SUCCESS")
            scaffoldState.snackbarHostState.showSnackbar(
                message = hostState.message,
                duration = SnackbarDuration.Short
            )
            hostViewModel.onMessageShown()
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 64.dp, start = 16.dp, end = 16.dp),
                hostState = it,
                snackbar = {
                    Snackbar(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = if (hostState.isSuccess) {
                            Color(0xFFDEF7EC)
                        } else {
                            Color(0xFFFDE8E8)
                        },
                        contentColor = if (hostState.isSuccess) {
                            Color(0xFF046C4E)
                        } else {
                            Color(0xFFC81E1E)
                        },
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            val contentColor = if (hostState.isSuccess) {
                                Color(0xFF046C4E)
                            } else {
                                Color(0xFFC81E1E)
                            }
                            Row {
                                Icon(
                                    painter = painterResource(
                                        if (hostState.isSuccess) {
                                            R.drawable.checkmark
                                        } else {
                                            R.drawable.oops
                                        }
                                    ),
                                    contentDescription = null,
                                    tint = contentColor
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = hostState.message.orEmpty(),
                                    color = contentColor,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.W500
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = hostState.description.orEmpty(),
                                color = contentColor,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.W300
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->

        val pullRefreshState = rememberPullRefreshState(
            refreshing = hostState.isLoading,
            onRefresh = {}
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pullRefresh(pullRefreshState, enabled = false)
        ) {
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = modifier
                    .fillMaxSize()
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
                                navController.navigate(
                                    Navigation.onBoardingDestination(
                                        userName,
                                        DisplayMode.CREATE_ACCOUNT
                                    )
                                )
                            } else {
                                navController.navigate(Routes.home)
                            }
                        },
                        onErrorShown = viewModel::onErrorShown
                    )
                }

                composable(
                    route = Routes.onBoarding,
                    arguments = listOf(navArgument(NavArguments.userName) {
                        type = NavType.StringType
                    })
                ) {
                    val viewModel: OnBoardingViewModel = hiltViewModel()
                    val state by viewModel.uiState.collectAsStateWithLifecycle()

                    OnBoardingScreen(
                        state = state,
                        onNameChanged = viewModel::onNameChanged,
                        onCitySelected = viewModel::onCitySelected,
                        onCreateAccountClicked = viewModel::createAccount,
                        onCompleted = {
                            if (state.displayMode == DisplayMode.CREATE_ACCOUNT) {
                                navController.navigate(Routes.home)
                            } else {
                                navController
                                    .previousBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("Name", it)
                                navController.popBackStack()
                            }
                        },
                        onCloseClicked = navController::popBackStack
                    )
                }

                composable(Routes.home) { entry ->
                    val newName by entry.savedStateHandle
                        .getStateFlow<String?>("Name", null)
                        .collectAsStateWithLifecycle()

                    HomeScreen(
                        onEventClicked = { eventId ->
                            navController.navigate(
                                Navigation.eventDetailsDestination(eventId)
                            )
                        },
                        onLoggedOut = {
                            navController.navigate(Routes.login) {
                                popUpTo(0)
                            }
                        },
                        onQrCodeClicked = {
                            qrCodeLauncher.launch(Unit)
                        },
                        onEditProfileClicked = {
                            navController.navigate(
                                Navigation.onBoardingDestination(
                                    it,
                                    DisplayMode.EDIT_ACCOUNT
                                )
                            )
                        },
                        newName = newName
                    )
                }

                composable(
                    route = Routes.eventDetails,
                    arguments = listOf(
                        navArgument(NavArguments.eventId) { type = NavType.StringType }
                    )
                ) {
                    val viewModel: EventDetailsViewModel = hiltViewModel()
                    val state by viewModel.uiStateFlow.collectAsStateWithLifecycle()

                    val context = LocalContext.current
                    EventDetailsScreen(
                        state = state,
                        onRegisterClicked = viewModel::registerToEvent,
                        onGoBackClicked = navController::popBackStack,
                        onQrCodeClicked = {
                            qrCodeLauncher.launch(Unit)
                        },
                        callOrganizerClicked = {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$it"))
                            context.startActivity(intent)
                        },
                        onRefresh = viewModel::refresh
                    )
                }
            }
            PullRefreshIndicator(
                refreshing = hostState.isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}