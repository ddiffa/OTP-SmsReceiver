package com.hellodiffa.myapplication.domain

import com.hellodiffa.myapplication.data.model.RequestOtp
import com.hellodiffa.myapplication.data.model.ResponseOtp

interface OtpRepository {

    suspend fun sendOtp(request: RequestOtp): ResponseOtp
}