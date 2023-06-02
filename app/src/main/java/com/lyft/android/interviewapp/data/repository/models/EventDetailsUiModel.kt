package com.lyft.android.interviewapp.data.repository.models

import androidx.compose.ui.graphics.Color
import com.lyft.android.interviewapp.data.remote.models.OrganizerInfo
import com.lyft.android.interviewapp.ui.theme.LightGrayBackgroundColor
import com.lyft.android.interviewapp.ui.theme.PrimaryColor
import com.lyft.android.interviewapp.ui.theme.TextColor

data class EventDetailsUiModel(
    val id: String = "",
    val dateTime: String = "", // 12:00 - 14:00 • 24 Травня 2023
    val name: String = "",
    val location: String = "",
    val volunteersCount: String = "", // 12/50
    val organizer: OrganizerInfo = OrganizerInfo(),
    val description: String = "",
    val photos: List<String> = emptyList(),
    val buttonConfigs: RegisterButtonConfigs = RegisterButtonConfigs.fromEventStatus(
        EventStatus.NONE,
        RegistrationStatus.UNKNOWN
    )
)

enum class RegistrationStatus {
    AVAILABLE, FULL, REGISTERED, CONFIRMED, UNKNOWN;

    companion object {

        fun fromInt(value: Int): RegistrationStatus {
            return RegistrationStatus.values().getOrElse(value) { UNKNOWN }
        }
    }

}

enum class EventStatus {
    NONE, DRAFT, ACTIVE, IN_PROGRESS, FINISHED, ARCHIVED;

    companion object {

        fun fromInt(value: Int): EventStatus {
            return EventStatus.values().getOrElse(value) { NONE }
        }
    }
}

class RegisterButtonConfigs(
    val color: Color,
    val text: String,
    val clickable: Boolean,
    val textColor: Color
) {
    companion object {
        fun fromEventStatus(
            eventStatus: EventStatus,
            registrationStatus: RegistrationStatus
        ): RegisterButtonConfigs {
            return when {
                eventStatus == EventStatus.FINISHED -> RegisterButtonConfigs(
                    LightGrayBackgroundColor,
                    "Завершено",
                    false,
                    Color(0xFF374151)
                )

                registrationStatus == RegistrationStatus.AVAILABLE -> RegisterButtonConfigs(
                    PrimaryColor,
                    "Приєднатись",
                    true,
                    Color.White
                )

                registrationStatus == RegistrationStatus.CONFIRMED -> RegisterButtonConfigs(
                    Color(0xFFDEF7EC),
                    "Підтверджено",
                    false,
                    Color(0xFF046C4E)
                )

                registrationStatus == RegistrationStatus.REGISTERED -> RegisterButtonConfigs(
                    Color(0xFFD5F5F6),
                    "Зареєстровано",
                    false,
                    Color(0xFF0694A2)
                )

                registrationStatus == RegistrationStatus.FULL -> RegisterButtonConfigs(
                    LightGrayBackgroundColor,
                    "Набір завершено",
                    false,
                    Color(0xFF374151)
                )

                registrationStatus == RegistrationStatus.UNKNOWN -> RegisterButtonConfigs(
                    Color(0xFF9CA3AF),
                    "...",
                    false,
                    TextColor
                )

                else -> RegisterButtonConfigs(
                    Color(0xFF9CA3AF),
                    "...",
                    false,
                    TextColor
                )
            }
        }
    }
}