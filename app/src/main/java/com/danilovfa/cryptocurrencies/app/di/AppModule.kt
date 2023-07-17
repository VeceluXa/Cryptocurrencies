package com.danilovfa.cryptocurrencies.app.di

import com.danilovfa.cryptocurrencies.app.viewmodel.SplashViewModel
import com.danilovfa.cryptocurrencies.app.viewmodel.DetailsViewModel
import com.danilovfa.cryptocurrencies.app.viewmodel.MainViewModel
import com.danilovfa.cryptocurrencies.app.viewmodel.UserSettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { DetailsViewModel(detailsUseCase = get()) }
    viewModel { MainViewModel(getCryptocurrenciesUseCase = get()) }
    viewModel {
        UserSettingsViewModel(
            getUserUseCase = get(),
            saveUserUseCase = get(),
            validateUserNameUseCase = get()
        )
    }
    viewModel { SplashViewModel(
        clearCacheUseCase = get(),
        getCryptocurrenciesUseCase = get()
    ) }
}