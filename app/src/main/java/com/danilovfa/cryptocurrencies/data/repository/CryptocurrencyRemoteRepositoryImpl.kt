package com.danilovfa.cryptocurrencies.data.repository

import android.util.Log
import com.danilovfa.cryptocurrencies.data.remote.CryptocurrencyAPI
import com.danilovfa.cryptocurrencies.data.remote.dto.ChartDto
import com.danilovfa.cryptocurrencies.data.remote.dto.CryptocurrenciesItemDto
import com.danilovfa.cryptocurrencies.data.remote.dto.ErrorDto
import com.danilovfa.cryptocurrencies.data.remote.mapper.ChartDtoMapper
import com.danilovfa.cryptocurrencies.data.remote.mapper.CryptocurrencyItemDtoMapper
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrenciesOrder
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyChart
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyItem
import com.danilovfa.cryptocurrencies.domain.model.ResponseWrapper
import com.danilovfa.cryptocurrencies.domain.repository.CryptocurrencyRemoteRepository
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.CHART_DAY
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.ERROR_BODY_IS_NULL
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.CHART_MAX
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.CHART_MONTH
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.CHART_WEEK
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.CHART_YEAR
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class CryptocurrencyRemoteRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val api: CryptocurrencyAPI
) : CryptocurrencyRemoteRepository {
    private val itemDtoMapper = CryptocurrencyItemDtoMapper()
    private val chartDtoMapper = ChartDtoMapper()

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
                    itemDtoMapper.fromEntity(dto)
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

    override suspend fun fetchCryptocurrencyCharts(id: String): ResponseWrapper<CryptocurrencyChart> {
        val listOfDates = listOf(CHART_DAY, CHART_WEEK, CHART_MONTH, CHART_YEAR, CHART_MAX)
        var chartsDto = mutableListOf<ChartDto>()
        listOfDates.forEach { days ->
            val chartDtoResponse = fetchChart(id, days)
            if (chartDtoResponse is ResponseWrapper.Error)
                return ResponseWrapper.Error(chartDtoResponse.errorMessage)

            val chart = (chartDtoResponse as ResponseWrapper.Success).data
            chartsDto.add(chart)
        }

        val charts = chartsDto.map { dto ->
            chartDtoMapper.fromEntity(dto)
        }

        val domainChart = CryptocurrencyChart(
            id = id,
            lastDay = charts[0],
            lastWeek = charts[1],
            lastMonth = charts[2],
            lastYear = charts[3],
            lastMax = charts[4]
        )

        return ResponseWrapper.Success(domainChart)
    }

    private suspend fun fetchChart(id: String, days: String): ResponseWrapper<ChartDto> {
        val chartDtoResponse: Response<ChartDto>

        try {
            withContext(ioDispatcher) {
                chartDtoResponse = api.getChart(id, days)
            }
        } catch (e: Exception) {
             return ResponseWrapper.Error("No internet connection")
        }

        return if (chartDtoResponse.isSuccessful) {
            if (chartDtoResponse.body() != null) {
                ResponseWrapper.Success(chartDtoResponse.body()!!)
            } else {
                ResponseWrapper.Error(ERROR_BODY_IS_NULL)
            }
        } else {
            val jsonString = chartDtoResponse.errorBody()!!.string()
            val gson = Gson()
            Log.d("RemoteRepo", "fetchChart: $jsonString")
            val error = gson.fromJson(jsonString, ErrorDto::class.java)
            ResponseWrapper.Error(error.status.errorMessage)
        }

    }
}