package com.danilovfa.cryptocurrencies.data.di

import com.danilovfa.cryptocurrencies.data.repository.CryptocurrencyLocalRepositoryImpl
import com.danilovfa.cryptocurrencies.data.repository.CryptocurrencyRemoteRepositoryImpl
import com.danilovfa.cryptocurrencies.data.repository.UserRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    single { CryptocurrencyLocalRepositoryImpl() }
    single { CryptocurrencyRemoteRepositoryImpl() }
    single { UserRepositoryImpl() }
}