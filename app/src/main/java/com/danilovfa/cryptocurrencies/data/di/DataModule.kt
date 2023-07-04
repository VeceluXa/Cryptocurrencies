package com.danilovfa.cryptocurrencies.data.di

import com.danilovfa.cryptocurrencies.data.remote.CryptocurrencyAPI
import com.danilovfa.cryptocurrencies.data.repository.CryptocurrencyLocalRepositoryImpl
import com.danilovfa.cryptocurrencies.data.repository.CryptocurrencyRemoteRepositoryImpl
import com.danilovfa.cryptocurrencies.data.repository.UserRepositoryImpl
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        val retrofit: Retrofit = get()
        retrofit.create(CryptocurrencyAPI::class.java)
    }

    single { CryptocurrencyLocalRepositoryImpl(remoteRepository = get()) }
    single { CryptocurrencyRemoteRepositoryImpl(api = get()) }
    single { UserRepositoryImpl() }
}