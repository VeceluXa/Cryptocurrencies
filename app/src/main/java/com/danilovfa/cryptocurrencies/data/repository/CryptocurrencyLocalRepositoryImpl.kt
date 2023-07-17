package com.danilovfa.cryptocurrencies.data.repository

import com.danilovfa.cryptocurrencies.data.local.CryptocurrencyDao
import com.danilovfa.cryptocurrencies.data.local.mapper.CryptocurrencyItemEntityMapper
import com.danilovfa.cryptocurrencies.data.local.model.CryptocurrencyItemEntity
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrenciesOrder
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyDetails
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyItem
import com.danilovfa.cryptocurrencies.domain.model.Resource
import com.danilovfa.cryptocurrencies.domain.repository.CryptocurrencyLocalRepository
import com.danilovfa.cryptocurrencies.domain.repository.CryptocurrencyRemoteRepository
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.PER_PAGE_DEFAULT
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CryptocurrencyLocalRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dao: CryptocurrencyDao,
    private val remoteRepository: CryptocurrencyRemoteRepository
) : CryptocurrencyLocalRepository {
    private val itemEntityMapper = CryptocurrencyItemEntityMapper()
    override suspend fun clearCache() {
        withContext(ioDispatcher) {
            dao.clearCache()
        }
    }

    override suspend fun getCryptocurrencies(
        page: Int,
        order: CryptocurrenciesOrder
    ): Flow<Resource<List<CryptocurrencyItem>>> = flow {
        emit(Resource.Loading())

        var items: List<CryptocurrencyItem>
        withContext(ioDispatcher) {
            items = dao.getItemsByPage(offset = page * PER_PAGE_DEFAULT).map { entity ->
                itemEntityMapper.fromEntity(entity)
            }
        }
        if (items.isEmpty()) {
            val response = remoteRepository.fetchCryptocurrencies(page, order)
            if (response is Resource.Success) {
                val entity = response.data.map { domain ->
                    itemEntityMapper.fromDomain(domain)
                }
                withContext(ioDispatcher) {
                    dao.setItems(entity)
                }
            }
            emit(response)
        } else {
            emit(Resource.Success(data = items))
        }

    }

    override suspend fun getCryptocurrencyDetails(id: String): CryptocurrencyDetails {
        TODO("Not yet implemented")
    }
}