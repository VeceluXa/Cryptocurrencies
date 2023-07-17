package com.danilovfa.cryptocurrencies.domain.model

sealed class Resource<T>{
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val errorMessage: String) : Resource<T>()
    class Loading<T> : Resource<T>()
}
