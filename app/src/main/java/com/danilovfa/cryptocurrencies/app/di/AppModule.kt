package com.danilovfa.cryptocurrencies.app.di

import com.danilovfa.cryptocurrencies.app.viewmodel.DetailsViewModel
import com.danilovfa.cryptocurrencies.app.viewmodel.MainViewModel
import com.danilovfa.cryptocurrencies.app.viewmodel.UserSettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { DetailsViewModel(detailsUseCase = get()) }
    viewModel {
        MainViewModel(
            getCryptocurrenciesByNameUseCase = get(),
            getCryptocurrenciesByPriceUseCase = get()
        )
    }
    viewModel {
        UserSettingsViewModel(
            getUserUseCase = get(),
            saveUserUseCase = get()
        )
    }
}