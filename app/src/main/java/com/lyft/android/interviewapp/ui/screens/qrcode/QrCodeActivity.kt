package com.lyft.android.interviewapp.ui.screens.qrcode

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.google.android.material.snackbar.Snackbar
import com.lyft.android.interviewapp.databinding.ActivityQrCodeBinding

class QrCodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQrCodeBinding
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQrCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val scannerView = binding.scannerView

        codeScanner = CodeScanner(this, scannerView)

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            setResult(RESULT_OK, Intent().putExtra(QR_CODE_CONTENT_RESULT_KEY, it.text))
            finish()
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(
                    this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

//        scannerView.setOnClickListener {
//            codeScanner.startPreview()
//        }
    }

    private val cameraPermissionRequestCode = 1

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                cameraPermissionRequestCode
            )
        } else {
            codeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == cameraPermissionRequestCode && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
            codeScanner.startPreview()
        } else {
            Snackbar.make(
                binding.root,
                "Для  сканування QR-коду необхідний доступ до камери",
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction("Надати") {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.CAMERA),
                        cameraPermissionRequestCode
                    )
                }
                .show()
        }
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}
