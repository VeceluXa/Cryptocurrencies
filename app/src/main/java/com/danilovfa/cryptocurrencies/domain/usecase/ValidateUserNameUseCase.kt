package com.danilovfa.cryptocurrencies.domain.usecase

import android.content.Context
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.domain.model.ValidatorResponse

class ValidateUserNameUseCase(
    private val context: Context
) {
    fun execute(name: String): ValidatorResponse {
        return if (name.length in 1..20)
            ValidatorResponse.Success
        else
            ValidatorResponse.Error(messageId = R.string.save_user_error)
    }
}