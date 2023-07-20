package com.danilovfa.cryptocurrencies.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ErrorDto(
    @SerializedName("status")
    val status: StatusDto
)