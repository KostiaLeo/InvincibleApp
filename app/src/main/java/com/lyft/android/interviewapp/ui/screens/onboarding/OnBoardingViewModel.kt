package com.lyft.android.interviewapp.ui.screens.onboarding

import android.content.SharedPreferences
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyft.android.interviewapp.data.repository.IdentityRepository
import com.lyft.android.interviewapp.di.isSignedIn
import com.lyft.android.interviewapp.ui.navigation.NavArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val identityRepository: IdentityRepository,
    private val sharedPreferences: SharedPreferences,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val displayMode = savedStateHandle.get<String>(NavArguments.displayMode)
        ?.let { DisplayMode.valueOf(it) } ?: DisplayMode.CREATE_ACCOUNT

    private val nameStateFlow = MutableStateFlow(
        savedStateHandle.get<String>(NavArguments.userName).orEmpty()
    )
    private val selectedCityStateFlow = MutableStateFlow<City?>(null)
    private val isAccountCreatedStateFlow = MutableStateFlow(false)
    private val showProgressStateFlow = MutableStateFlow(false)
    val uiState = combine(
        nameStateFlow,
        selectedCityStateFlow,
        isAccountCreatedStateFlow,
        showProgressStateFlow
    ) { name, selectedCity, isAccountCreated, showProgress ->
        OnBoardingUiState(
            name = name,
            selectedCity = selectedCity,
            isReadyToRegister = name.isNotBlank() && selectedCity != null,
            isAccountAlterationCompleted = isAccountCreated,
            showProgress = showProgress,
            displayMode = displayMode
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        OnBoardingUiState()
    )

    fun onNameChanged(newName: String) {
        nameStateFlow.value = newName
    }

    fun onCitySelected(city: City) {
        selectedCityStateFlow.value = city
    }

    fun createAccount() {
        viewModelScope.launch {
            val name = uiState.value.name
            val city = uiState.value.selectedCity ?: return@launch
            showProgressStateFlow.value = true
            identityRepository.createAccount(name, city.code)
            showProgressStateFlow.value = false
            sharedPreferences.isSignedIn = true
            isAccountCreatedStateFlow.value = true
        }
    }
}

data class OnBoardingUiState(
    val name: String = "",
    val selectedCity: City? = null,
    val isReadyToRegister: Boolean = false,
    val isAccountAlterationCompleted: Boolean = false,
    val showProgress: Boolean = false,
    val displayMode: DisplayMode = DisplayMode.CREATE_ACCOUNT
)

enum class DisplayMode {
    CREATE_ACCOUNT,
    EDIT_ACCOUNT
}

data class City(
    val name: String,
    val code: Int
)

object Cities {
    operator fun invoke(): List<City> {
        return listOf(
            "Вінниця" of 0,
            "Київ" of 1,
            "Житомир" of 2,
        )
    }

    private infix fun String.of(code: Int): City {
        return City(this, code)
    }
}