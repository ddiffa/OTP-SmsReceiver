package com.hellodiffa.myapplication.data.model

data class ResponseOtp(
	val msgId: String? = null,
	val expireIn: Int? = null,
	val msgCount: Int? = null,
	val status: String? = null
)
