package com.danilovfa.cryptocurrencies.data.local.mapper

import com.danilovfa.cryptocurrencies.data.local.model.CryptocurrencyDetailsEntity
import com.danilovfa.cryptocurrencies.utils.Mapper
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyDetails

class CryptocurrencyDetailsEntityMapper :
    Mapper<CryptocurrencyDetailsEntity, CryptocurrencyDetails> {
    private val itemMapper = CryptocurrencyItemEntityMapper()
    private val chartsMapper = CryptocurrencyChartEntityMapper()

    override fun fromEntity(entity: CryptocurrencyDetailsEntity): CryptocurrencyDetails {
        return CryptocurrencyDetails(
            coinDetails = itemMapper.fromEntity(entity.itemDetails),
            charts = chartsMapper.fromEntity(entity.charts!!)
        )
    }

    override fun fromDomain(domain: CryptocurrencyDetails): CryptocurrencyDetailsEntity {
        return CryptocurrencyDetailsEntity(
            itemDetails = itemMapper.fromDomain(domain.coinDetails),
            charts = chartsMapper.fromDomain(domain.charts)
        )
    }
}