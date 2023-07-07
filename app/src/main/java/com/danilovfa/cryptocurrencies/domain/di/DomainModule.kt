package com.danilovfa.cryptocurrencies.domain.di

import com.danilovfa.cryptocurrencies.domain.model.ValidatorResponse
import com.danilovfa.cryptocurrencies.domain.usecase.GetCryptocurrenciesByNameUseCase
import com.danilovfa.cryptocurrencies.domain.usecase.GetCryptocurrenciesByPriceUseCase
import com.danilovfa.cryptocurrencies.domain.usecase.GetCryptocurrencyDetailsUseCase
import com.danilovfa.cryptocurrencies.domain.usecase.GetUserUseCase
import com.danilovfa.cryptocurrencies.domain.usecase.SaveUserUseCase
import com.danilovfa.cryptocurrencies.domain.usecase.ValidateUserNameUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val domainModule = module {
    factory { GetCryptocurrenciesByNameUseCase(repository = get()) }
    factory { GetCryptocurrenciesByPriceUseCase(repository = get()) }
    factory { GetCryptocurrencyDetailsUseCase(repository = get()) }
    factory { GetUserUseCase(repository = get()) }
    factory { SaveUserUseCase(repository = get()) }
    factory { ValidateUserNameUseCase(androidContext()) }
}