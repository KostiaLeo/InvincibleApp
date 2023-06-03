package com.lyft.android.interviewapp.ui.screens.login

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.lyft.android.interviewapp.R
import com.lyft.android.interviewapp.data.signin.SignInGoogleContract
import com.lyft.android.interviewapp.ui.theme.AppTheme
import com.lyft.android.interviewapp.ui.theme.PrimaryColor
import com.lyft.android.interviewapp.ui.theme.TextColor

private const val googleSignInRequestCode = 1

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(
    state: LoginUiState,
    onAuthResult: (Task<GoogleSignInAccount>?) -> Unit,
    onLoginCompleted: (isNewUser: Boolean, userName: String) -> Unit,
    onErrorShown: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        (context as AppCompatActivity).run {
            window.navigationBarColor = getColor(R.color.primaryColor)
            window.statusBarColor = getColor(R.color.primaryColor)
        }
    }
    val authResultLauncher = rememberLauncherForActivityResult(
        contract = SignInGoogleContract,
        onResult = {
            Log.d("LOGIN_FLOW", "onResult: $it")
            onAuthResult(it)
        }
    )

    if (state.isLoginCompleted) {
        LaunchedEffect(Unit) {
            onLoginCompleted(state.isNewUser, state.userName)
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    val scaffoldState = rememberScaffoldState()

    if (state.errorMessage != null) {
        Log.d("LOGIN_FLOW", "error msg on ui: ${state.errorMessage}")
        LaunchedEffect(state.errorMessage) {
            scaffoldState.snackbarHostState.showSnackbar(state.errorMessage)
            onErrorShown()
        }
    }

    Scaffold(
        backgroundColor = PrimaryColor,
        topBar = {},
        content = { paddingValues ->
            val pullRefreshState = rememberPullRefreshState(
                refreshing = state.showProgress,
                onRefresh = {}
            )
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState, enabled = false)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.app_logo),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Цифрова\n" +
                                    "волонтерська\n" +
                                    "патформа",
                            color = Color.White,
                            fontWeight = FontWeight.W500,
                            fontSize = 14.sp
                        )
                    }
                }
                PullRefreshIndicator(
                    refreshing = state.showProgress, state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                elevation = 0.dp,
                backgroundColor = PrimaryColor,
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = TextColor
                    ),
                    onClick = {
                        Log.d("LOGIN_FLOW", "Button clicked")
                        authResultLauncher.launch(googleSignInRequestCode)
                    }
                ) {
                    Image(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        painter = painterResource(R.drawable.google_svg),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = "Увійти через Google",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500
                    )
                }
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 64.dp, start = 16.dp, end = 16.dp),
                hostState = it,
                snackbar = { snackbarData ->
                    Snackbar(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = Color(0xFFFDE8E8),
                        contentColor = Color(0xFFC81E1E),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            val contentColor = Color(0xFFC81E1E)
                            Row {
                                Icon(
                                    painter = painterResource(
                                        R.drawable.oops
                                    ),
                                    contentDescription = null,
                                    tint = contentColor
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Упс, щось пішло не так",
                                    color = contentColor,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.W500
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = snackbarData.message,
                                color = contentColor,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.W300
                            )
                        }
                    }
                }
            )
        }
    )
}

@Preview
@Composable
fun LoginScreenPreview() {
    AppTheme {
        LoginScreen(
            state = LoginUiState(),
            onAuthResult = {},
            onLoginCompleted = { _, _ -> },
            onErrorShown = {}
        )
    }
}