package com.danilovfa.cryptocurrencies.data.repository

import android.content.Context
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.data.remote.CryptocurrencyAPI
import com.danilovfa.cryptocurrencies.data.remote.dto.CryptocurrenciesItemDto
import com.danilovfa.cryptocurrencies.data.remote.dto.ErrorDto
import com.danilovfa.cryptocurrencies.data.remote.mapper.CryptocurrencyItemDtoMapper
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrenciesOrder
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyDetails
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyItem
import com.danilovfa.cryptocurrencies.domain.model.ResponseWrapper
import com.danilovfa.cryptocurrencies.domain.repository.CryptocurrencyRemoteRepository
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.ERROR_BODY_IS_NULL
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.get
import retrofit2.Response

class CryptocurrencyRemoteRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val api: CryptocurrencyAPI
) : CryptocurrencyRemoteRepository {
    private val dtoMapper = CryptocurrencyItemDtoMapper()
    override suspend fun fetchCryptocurrencies(
        page: Int, order: CryptocurrenciesOrder
    ): ResponseWrapper<List<CryptocurrencyItem>> {
        val apiResponse: Response<List<CryptocurrenciesItemDto>>

        try {
            withContext(ioDispatcher) {
                apiResponse = api.getCryptocurrenciesByPage(
                    page = page, order = order.apiMessage
                )
            }
        } catch (e: Exception) {
            return ResponseWrapper.Error("No internet connection")
        }

        val domainResponse = if (apiResponse.isSuccessful) {
            if (apiResponse.body() != null) {
                val data = apiResponse.body()!!.map { dto ->
                    dtoMapper.fromEntity(dto)
                }
                ResponseWrapper.Success(data)
            } else ResponseWrapper.Error(ERROR_BODY_IS_NULL)

        } else {
            val jsonString = apiResponse.errorBody()!!.string()
            val gson = Gson()
            val error = gson.fromJson(jsonString, ErrorDto::class.java)
            ResponseWrapper.Error(error.status.errorMessage)
        }
        return domainResponse
    }

    override suspend fun fetchCryptocurrencyDetails(id: String): CryptocurrencyDetails {
        TODO("Not yet implemented")
    }


}