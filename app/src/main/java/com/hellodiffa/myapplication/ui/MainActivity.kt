package com.hellodiffa.myapplication.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.hellodiffa.myapplication.R
import com.hellodiffa.myapplication.common.ResultState
import com.hellodiffa.myapplication.data.model.RequestOtp
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewModel by inject<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeData()
        btnOtp.setOnClickListener {
            requestOtp()
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
