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
class AppNavHostViewModel @Inject constructor(
    private val qrCodeScannedUseCase: QrCodeScannedUseCase
) : ViewModel() {

    var uiState by mutableStateOf(AppNavHostUiState())
        private set

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("ERROR_NAV_HOST", throwable.localizedMessage, throwable)
        uiState = uiState.copy(
            isLoading = false,
            isSuccess = false,
            message = "Упс, щось пішло не так",
            description = "Ми не змогли підтвердити вашу присутність"
        )
    }

    fun onQrCodeScanned(result: String?) {
        if (result == null) {
            return
        }
        viewModelScope.launch(exceptionHandler) {
            uiState = uiState.copy(isLoading = true)
            qrCodeScannedUseCase(result)
            uiState = uiState.copy(
                isLoading = false,
                isSuccess = true,
                message = "Ваша присутність підтверджена",
                description = "Дякуємо за участь у місії"
            )
        }
    }

    fun onMessageShown() {
        uiState = uiState.copy(message = null)
    }
}

data class AppNavHostUiState(
    val message: String? = null,
    val description: String? = null,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false
)