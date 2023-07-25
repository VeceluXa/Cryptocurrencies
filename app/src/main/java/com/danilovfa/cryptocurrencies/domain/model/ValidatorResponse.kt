package com.danilovfa.cryptocurrencies.domain.model

sealed class ValidatorResponse {
    object Success : ValidatorResponse()
    class Error(val messageId: Int) : ValidatorResponse()
}
