package com.danilovfa.cryptocurrencies.data.local.mapper

import com.danilovfa.cryptocurrencies.data.local.model.CryptocurrencyChartEntity
import com.danilovfa.cryptocurrencies.utils.Mapper
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyChart

class CryptocurrencyChartEntityMapper : Mapper<CryptocurrencyChartEntity, CryptocurrencyChart> {
    private val timestampMapper = ChartTimestampEntityMapper()

    override fun fromEntity(entity: CryptocurrencyChartEntity): CryptocurrencyChart {
        return CryptocurrencyChart(
            id = entity.chartId,
            lastDay = entity.lastDay.map { timestampMapper.fromEntity(it) },
            lastWeek = entity.lastWeek.map { timestampMapper.fromEntity(it) },
            lastMonth = entity.lastMonth.map { timestampMapper.fromEntity(it) },
            lastYear = entity.lastYear.map { timestampMapper.fromEntity(it) },
            lastMax = entity.lastMax.map { timestampMapper.fromEntity(it) },
        )
    }

    override fun fromDomain(domain: CryptocurrencyChart): CryptocurrencyChartEntity {
        return CryptocurrencyChartEntity(
            chartId = domain.id,
            lastDay = domain.lastDay.map { timestampMapper.fromDomain(it) },
            lastWeek = domain.lastWeek.map { timestampMapper.fromDomain(it) },
            lastMonth = domain.lastMonth.map { timestampMapper.fromDomain(it) },
            lastYear = domain.lastYear.map { timestampMapper.fromDomain(it) },
            lastMax = domain.lastMax.map { timestampMapper.fromDomain(it) }
        )
    }
}