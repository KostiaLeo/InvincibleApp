package com.lyft.android.interviewapp.ui.screens.qrcode

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

const val QR_CODE_CONTENT_RESULT_KEY = "qr_code_content"

object QrCodeActivityResultContract : ActivityResultContract<Unit, String?>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(context, QrCodeActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        return intent?.getStringExtra(QR_CODE_CONTENT_RESULT_KEY)
    }
}