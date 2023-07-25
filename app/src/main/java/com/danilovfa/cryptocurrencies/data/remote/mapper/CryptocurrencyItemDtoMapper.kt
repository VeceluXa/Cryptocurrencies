package com.danilovfa.cryptocurrencies.data.remote.mapper

import com.danilovfa.cryptocurrencies.data.remote.dto.CryptocurrenciesItemDto
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyItem
import com.danilovfa.cryptocurrencies.utils.Mapper

class CryptocurrencyItemDtoMapper : Mapper<CryptocurrenciesItemDto, CryptocurrencyItem> {
    override fun fromEntity(entity: CryptocurrenciesItemDto): CryptocurrencyItem {
        return CryptocurrencyItem(
            id = entity.id,
            name = entity.name,
            symbol = entity.symbol,
            price = entity.currentPrice,
            marketCap = entity.marketCap,
            priceChanged24hPercentage = entity.priceChangePercentage24h,
            imageUrl = entity.image,
        )
    }

    override fun fromDomain(domain: CryptocurrencyItem): CryptocurrenciesItemDto {
        TODO("Not yet implemented")
    }
}