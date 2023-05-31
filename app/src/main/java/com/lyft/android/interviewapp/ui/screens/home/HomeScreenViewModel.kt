package com.lyft.android.interviewapp.ui.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyft.android.interviewapp.ui.screens.qrcode.QrCodeScannedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val qrCodeScannedUseCase: QrCodeScannedUseCase
) : ViewModel() {

    var screenUiState by mutableStateOf(HomeScreenUiState())
        private set

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("ERROR_HOME", throwable.localizedMessage, throwable)
        screenUiState = screenUiState.copy(
            isLoading = false,
            errorMessage = "Щось пішло не так"
        )
    }

    fun onQrCodeScanned(result: String?) {
        viewModelScope.launch(exceptionHandler) {
            screenUiState = screenUiState.copy(isLoading = true)
            qrCodeScannedUseCase(result)
            screenUiState = screenUiState.copy(
                isLoading = false,
                successMessage = "Ви успішно зареєструвались на захід"
            )
        }
    }

    fun onErrorShown() {
        screenUiState = screenUiState.copy(errorMessage = null)
    }

    fun onSuccessShown() {
        screenUiState = screenUiState.copy(successMessage = null)
    }
}

data class HomeScreenUiState(
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isLoading: Boolean = false
)