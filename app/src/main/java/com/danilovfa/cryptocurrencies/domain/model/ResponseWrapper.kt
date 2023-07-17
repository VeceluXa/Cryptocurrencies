package com.danilovfa.cryptocurrencies.domain.model

sealed class ResponseWrapper<T>{
    class Success<T>(val data: T) : ResponseWrapper<T>()
    class Error<T>(val errorMessage: String) : ResponseWrapper<T>()
    class Loading<T> : ResponseWrapper<T>()
}
