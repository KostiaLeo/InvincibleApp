package com.lyft.android.interviewapp.data.repository.models

import androidx.compose.ui.graphics.Color
import com.lyft.android.interviewapp.ui.theme.PrimaryColor

data class EventDetailsUiModel(
    val id: String = "",
    val dateTime: String = "", // 12:00 - 14:00 • 24 Травня 2023
    val name: String = "",
    val location: String = "",
    val volunteersCount: String = "", // 12/50
    val organizer: String = "",
    val description: String = "",
    val duties: String = "",
    val photos: List<String> = emptyList(),
    val registrationStatus: RegistrationStatus = RegistrationStatus.UNKNOWN
)

enum class RegistrationStatus {
    AVAILABLE, REGISTERED, COMPLETED, UNKNOWN
}

class RegisterButtonConfigs(
    val color: Color,
    val text: String,
    val clickable: Boolean
) {
    companion object {
        fun fromRegistrationStatus(registrationStatus: RegistrationStatus): RegisterButtonConfigs {
            return when (registrationStatus) {
                RegistrationStatus.AVAILABLE -> RegisterButtonConfigs(
                    PrimaryColor,
                    "Приєднатись",
                    true
                )

                RegistrationStatus.COMPLETED -> RegisterButtonConfigs(
                    Color(0xFF9CA3AF),
                    "Завершено",
                    false
                )

                RegistrationStatus.REGISTERED -> RegisterButtonConfigs(
                    Color(0xFF0E9F6E),
                    "Зареєстровано",
                    false
                )

                RegistrationStatus.UNKNOWN -> RegisterButtonConfigs(
                    Color(0xFF9CA3AF),
                    "Сталася помилка :(",
                    false
                )
            }
        }
    }
}