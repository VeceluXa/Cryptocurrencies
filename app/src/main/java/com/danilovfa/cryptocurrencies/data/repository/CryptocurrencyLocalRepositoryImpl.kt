package com.danilovfa.cryptocurrencies.data.repository

import com.danilovfa.cryptocurrencies.data.local.CryptocurrencyDao
import com.danilovfa.cryptocurrencies.data.local.mapper.CryptocurrencyChartEntityMapper
import com.danilovfa.cryptocurrencies.data.local.mapper.CryptocurrencyDetailsEntityMapper
import com.danilovfa.cryptocurrencies.data.local.mapper.CryptocurrencyItemEntityMapper
import com.danilovfa.cryptocurrencies.data.local.model.CryptocurrencyDetailsEntity
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrenciesOrder
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyDetails
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyItem
import com.danilovfa.cryptocurrencies.domain.model.ResponseWrapper
import com.danilovfa.cryptocurrencies.domain.repository.CryptocurrencyLocalRepository
import com.danilovfa.cryptocurrencies.domain.repository.CryptocurrencyRemoteRepository
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.PER_PAGE_DEFAULT
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class CryptocurrencyLocalRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dao: CryptocurrencyDao,
    private val remoteRepository: CryptocurrencyRemoteRepository
) : CryptocurrencyLocalRepository {
    private val itemEntityMapper = CryptocurrencyItemEntityMapper()
    private val detailsEntityMapper = CryptocurrencyDetailsEntityMapper()
    private val chartsEntityMapper = CryptocurrencyChartEntityMapper()

    override suspend fun clearCache() {
        withContext(ioDispatcher) {
            dao.clearCache()
        }
    }

    override suspend fun getCryptocurrencies(
        page: Int,
        order: CryptocurrenciesOrder
    ): Flow<ResponseWrapper<List<CryptocurrencyItem>>> = flow {
        emit(ResponseWrapper.Loading())

        var items: List<CryptocurrencyItem>
        withContext(ioDispatcher) {
            items = dao.getItemsByPage(offset = (page - 1) * PER_PAGE_DEFAULT).map { entity ->
                itemEntityMapper.fromEntity(entity)
            }
        }
        if (items.isEmpty()) {
            val response = remoteRepository.fetchCryptocurrencies(page, order)
            if (response is ResponseWrapper.Success) {
                val entity = response.data.map { domain ->
                    itemEntityMapper.fromDomain(domain)
                }
                withContext(ioDispatcher) {
                    dao.setItems(entity)
                }
            }
            emit(response)
        } else {
            emit(ResponseWrapper.Success(data = items))
        }

    }

    override suspend fun getCryptocurrencyDetails(id: String): Flow<ResponseWrapper<CryptocurrencyDetails>> =
        flow {
            emit(ResponseWrapper.Loading())

            var detailsEntity: CryptocurrencyDetailsEntity
            withContext(ioDispatcher) {
                detailsEntity = dao.getDetails(id)
            }

            if (detailsEntity.charts == null) {
                val chartsResponse = remoteRepository.fetchCryptocurrencyCharts(id)

                if (chartsResponse is ResponseWrapper.Error) {
                    emit(ResponseWrapper.Error(chartsResponse.errorMessage))
                    return@flow
                }

                withContext(ioDispatcher) {
                    val charts = (chartsResponse as ResponseWrapper.Success).data
                    dao.setChart(chartsEntityMapper.fromDomain(charts))
                }
                withContext(ioDispatcher) {
                    detailsEntity = dao.getDetails(id)
                }
            }

            val details = detailsEntityMapper.fromEntity(detailsEntity)
            emit(ResponseWrapper.Success(details))
        }
}