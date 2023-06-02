package com.lyft.android.interviewapp.ui.screens.qrcode

import android.util.Log
import com.lyft.android.interviewapp.data.repository.VolunteerEventsRepository
import javax.inject.Inject

class QrCodeScannedUseCase @Inject constructor(
    private val eventsRepository: VolunteerEventsRepository
) {
    suspend operator fun invoke(qrCodeContent: String) {
        Log.d("QR_CODE", qrCodeContent)

        if (qrCodeContent.isBlank()) {
            error("QR code content is null or blank")
        }
        eventsRepository.confirmPresence(qrCodeContent)
    }
}