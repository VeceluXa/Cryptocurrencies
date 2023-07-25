package com.danilovfa.cryptocurrencies.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyDetails
import com.danilovfa.cryptocurrencies.domain.model.ResponseWrapper
import com.danilovfa.cryptocurrencies.domain.usecase.GetCryptocurrencyDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

class DetailsViewModel(
    private val detailsUseCase: GetCryptocurrencyDetailsUseCase
) : ViewModel() {
    private val _details =
        MutableStateFlow<CryptocurrencyDetails?>(null)
    val details = _details.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    fun getDetails(id: String) {
        viewModelScope.launch {
            detailsUseCase.execute(id).collectLatest { response ->
                when (response) {
                    is ResponseWrapper.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = response.errorMessage
                    }

                    is ResponseWrapper.Loading -> {
                        _isLoading.value = true
                    }

                    is ResponseWrapper.Success -> {
                        _isLoading.value = false
                        _details.value = response.data
                    }
                }
            }
        }
    }

    fun priceChangedToString(number: Double): String {
        var formattedNumber = String.format("%.${if (number == 0.0) 0 else 2}f", abs(number))
        val indexOfDecimal = formattedNumber.indexOf('.')
        formattedNumber = if (indexOfDecimal >= 0) {
            val trailingZeros = formattedNumber.substring(indexOfDecimal + 1).count { it == '0' }
            if (trailingZeros > 2) {
                String.format("%.${trailingZeros}f", number)
            } else {
                formattedNumber
            }
        } else {
            formattedNumber
        }

        return if (number >= 0)
            "+$formattedNumber %"
        else
            "-$formattedNumber %"
    }

    fun priceToString(price: Double): String {
        return "$price $"
    }

    fun suffixPriceToString(price: Double): String {
        val suffixes = listOf("", "K", "M", "B", "T")
        var num = price
        var suffixIndex = 0

        while (num >= 1000 && suffixIndex < suffixes.lastIndex) {
            num /= 1000
            suffixIndex++
        }

        val formattedString = String.format("%.2f", num)
        val suffix = suffixes[suffixIndex]

        return "\$ $formattedString $suffix"
    }
}