package com.danilovfa.cryptocurrencies.app.viewmodel

import androidx.lifecycle.ViewModel
import com.danilovfa.cryptocurrencies.domain.usecase.GetCryptocurrencyDetailsUseCase

class DetailsViewModel(
    private val detailsUseCase: GetCryptocurrencyDetailsUseCase
) : ViewModel() {

}