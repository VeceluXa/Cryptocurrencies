package com.danilovfa.cryptocurrencies.app.viewmodel

import androidx.lifecycle.ViewModel
import com.danilovfa.cryptocurrencies.domain.usecase.GetCryptocurrenciesUseCase

class MainViewModel(
    private val getCryptocurrenciesUseCase: GetCryptocurrenciesUseCase
) : ViewModel() {

}