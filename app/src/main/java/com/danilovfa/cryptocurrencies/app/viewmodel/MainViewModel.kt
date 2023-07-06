package com.danilovfa.cryptocurrencies.app.viewmodel

import androidx.lifecycle.ViewModel
import com.danilovfa.cryptocurrencies.domain.usecase.GetCryptocurrenciesByNameUseCase
import com.danilovfa.cryptocurrencies.domain.usecase.GetCryptocurrenciesByPriceUseCase

class MainViewModel(
    private val getCryptocurrenciesByNameUseCase: GetCryptocurrenciesByNameUseCase,
    private val getCryptocurrenciesByPriceUseCase: GetCryptocurrenciesByPriceUseCase
) : ViewModel() {

}