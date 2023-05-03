package com.lyft.android.interviewapp.ui.screens.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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

@Composable
fun LoginScreen(
    state: LoginUiState,
    onAuthResult: (Task<GoogleSignInAccount>?) -> Unit,
    onLoginCompleted: (isNewUser: Boolean, userName: String) -> Unit
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
        onResult = onAuthResult
    )

    if (state.isLoginCompleted) {
        LaunchedEffect(Unit) {
            onLoginCompleted(state.isNewUser, state.userName)
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Scaffold(
        backgroundColor = PrimaryColor,
        topBar = {},
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(R.drawable.app_logo), contentDescription = null)
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
            onLoginCompleted = { _, _ -> }
        )
    }
}