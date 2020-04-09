package com.hellodiffa.myapplication.ui

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.hellodiffa.myapplication.R
import com.hellodiffa.myapplication.common.ResultState
import com.hellodiffa.myapplication.data.model.RequestOtp
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_SMS_NO = "extra_sms_no"
        const val EXTRA_SMS_MESSAGE = "extra_sms_message"
    }

    private val viewModel by inject<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkRuntimePermission()
        getMessage()
        observeData()
        btnOtp.setOnClickListener {
            requestOtp()
        }
    }

    private fun getMessage() {
        val senderNo = intent.getStringExtra(EXTRA_SMS_NO)
        val senderMessage = intent.getStringExtra(EXTRA_SMS_MESSAGE)
        Log.e(
            "Message", """
            Sender  :  $senderNo
            Message : $senderMessage 
        """.trimIndent()
        )
        if (senderNo == "d\'bigbox.id") {
            val otp = senderMessage.substring(16, 21).trim()
            Log.e("Otp", otp)
            edt1.setText(otp[0].toString())
            edt2.setText(otp[1].toString())
            edt3.setText(otp[2].toString())
            edt4.setText(otp[3].toString())
        }
    }

    private fun requestOtp() {
        val request =
            RequestOtp(phoneNum = edtPhoneNumber.text.toString().trim(), digit = 4, expireIn = 2000)
        viewModel.sendOtp(request)
    }

    private fun observeData() {
        viewModel.otp.observe(this, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showLoading()
                }
                is ResultState.HasData -> {
                    hideLoading()
                    longToast(it.data.status.toString())
                }
                is ResultState.Error -> {
                    hideLoading()
                    longToast(resources.getString(it.errorMessage))
                }
                is ResultState.TimeOut -> {
                    hideLoading()
                    longToast(resources.getString(it.errorMessage))
                }
                is ResultState.NoInternetConnection -> {
                    hideLoading()
                    longToast(resources.getString(it.errorMessage))
                }
            }
        })
    }

    private fun checkRuntimePermission() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        Toast.makeText(
                            applicationContext,
                            "permission has been granted",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

            }).withErrorListener {
                Toast.makeText(applicationContext, it.name, Toast.LENGTH_SHORT).show()
            }.check()
    }

    private fun longToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

}
