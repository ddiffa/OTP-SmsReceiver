package com.hellodiffa.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class RequestOtp(

	@field:SerializedName("phoneNum")
	val phoneNum: String? = null,

	@field:SerializedName("expireIn")
	val expireIn: Int? = null,

	@field:SerializedName("digit")
	val digit: Int? = null
)