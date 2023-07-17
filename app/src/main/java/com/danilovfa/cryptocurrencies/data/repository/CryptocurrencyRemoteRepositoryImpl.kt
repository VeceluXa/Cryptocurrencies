package com.danilovfa.cryptocurrencies.data.repository

import com.danilovfa.cryptocurrencies.data.remote.CryptocurrencyAPI
import com.danilovfa.cryptocurrencies.data.remote.dto.CryptocurrenciesItemDto
import com.danilovfa.cryptocurrencies.data.remote.mapper.CryptocurrencyItemDtoMapper
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrenciesOrder
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyDetails
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyItem
import com.danilovfa.cryptocurrencies.domain.model.Resource
import com.danilovfa.cryptocurrencies.domain.repository.CryptocurrencyRemoteRepository
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.ERROR_BODY_IS_NULL
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class CryptocurrencyRemoteRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val api: CryptocurrencyAPI
) : CryptocurrencyRemoteRepository {
    private val dtoMapper = CryptocurrencyItemDtoMapper()
    override suspend fun fetchCryptocurrencies(
        page: Int,
        order: CryptocurrenciesOrder
    ): Resource<List<CryptocurrencyItem>> {
        val apiResponse: Response<List<CryptocurrenciesItemDto>>
        withContext(ioDispatcher) {
            apiResponse = api.getCryptocurrenciesByPage(
                page = page,
                order = order.message
            )
        }

        val domainResponse = if (apiResponse.isSuccessful) {
            if (apiResponse.body() != null) {
                val data = apiResponse.body()!!.map { dto ->
                    dtoMapper.fromEntity(dto)
                }
                Resource.Success(data)
            } else
                Resource.Error(ERROR_BODY_IS_NULL)

        } else {
            if (apiResponse.errorBody() != null)
                Resource.Error(ERROR_BODY_IS_NULL)
            else
                Resource.Error(apiResponse.errorBody()!!.string())
        }
        return domainResponse
    }

    override suspend fun fetchCryptocurrencyDetails(id: String): CryptocurrencyDetails {
        TODO("Not yet implemented")
    }


}