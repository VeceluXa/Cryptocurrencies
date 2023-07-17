package com.danilovfa.cryptocurrencies.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrenciesOrder
import com.danilovfa.cryptocurrencies.domain.model.ResponseWrapper
import com.danilovfa.cryptocurrencies.domain.usecase.ClearCacheUseCase
import com.danilovfa.cryptocurrencies.domain.usecase.GetCryptocurrenciesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val clearCacheUseCase: ClearCacheUseCase,
    private val getCryptocurrenciesUseCase: GetCryptocurrenciesUseCase
): ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            clearCacheUseCase.execute()
            getCryptocurrenciesUseCase
                .execute(0, CryptocurrenciesOrder.CAPITALIZATION_DESCENDING)
                .collect { resource ->
                    if (resource is ResponseWrapper.Success || resource is ResponseWrapper.Error)
                        _isLoading.value = false
                }
        }
    }
}