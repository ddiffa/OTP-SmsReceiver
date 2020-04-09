package com.hellodiffa.myapplication.data

import com.hellodiffa.myapplication.data.model.RequestOtp
import com.hellodiffa.myapplication.data.model.ResponseOtp
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import java.util.concurrent.TimeUnit

enum class BigBoxAPI {
    INSTANCE;

    private var api: Api

    init {
        val okHttp = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(makeLoggingInterceptor(true))
            .build()

        api = Retrofit.Builder()
            .baseUrl("https://api.thebigbox.id")
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp)
            .build()
            .create(Api::class.java)
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val log = HttpLoggingInterceptor()
        log.level =
            if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return log
    }

    internal suspend fun sendOtp(request: RequestOtp): ResponseOtp {
        return api.sendOtp("", request)
    }

    interface Api {
        @PUT("/sms-otp/1.0.0/otp/smsbroadcast")
        suspend fun sendOtp(
            @Header("x-api-key") apiKey: String,
            @Body request: RequestOtp
        ) : ResponseOtp
    }
}
