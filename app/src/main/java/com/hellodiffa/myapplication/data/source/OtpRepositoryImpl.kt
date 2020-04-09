package com.hellodiffa.myapplication.data.source

import com.hellodiffa.myapplication.data.BigBoxAPI
import com.hellodiffa.myapplication.data.model.RequestOtp
import com.hellodiffa.myapplication.data.model.ResponseOtp
import com.hellodiffa.myapplication.domain.OtpRepository

class OtpRepositoryImpl(private val api : BigBoxAPI) : OtpRepository {

    override suspend fun sendOtp(request: RequestOtp): ResponseOtp {
        return api.sendOtp(request)
    }

}