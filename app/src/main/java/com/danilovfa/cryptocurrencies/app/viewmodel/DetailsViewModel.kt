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
}