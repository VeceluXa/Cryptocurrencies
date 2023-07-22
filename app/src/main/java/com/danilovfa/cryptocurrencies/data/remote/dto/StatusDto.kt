package com.danilovfa.cryptocurrencies.data.remote.dto


import com.google.gson.annotations.SerializedName

data class StatusDto(
    @SerializedName("error_code")
    val errorCode: Int,
    @SerializedName("error_message")
    val errorMessage: String
)