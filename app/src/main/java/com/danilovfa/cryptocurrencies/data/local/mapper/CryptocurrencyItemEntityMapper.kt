package com.danilovfa.cryptocurrencies.data.local.mapper

import com.danilovfa.cryptocurrencies.data.local.model.CryptocurrencyItemEntity
import com.danilovfa.cryptocurrencies.utils.Mapper
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyItem

class CryptocurrencyItemEntityMapper : Mapper<CryptocurrencyItemEntity, CryptocurrencyItem> {
    override fun fromEntity(entity: CryptocurrencyItemEntity): CryptocurrencyItem {
        return CryptocurrencyItem(
            id = entity.coinId,
            name = entity.name,
            symbol = entity.symbol,
            price = entity.price,
            marketCap = entity.marketCap,
            imageUrl = entity.imageUrl
        )
    }

    override fun fromDomain(domain: CryptocurrencyItem): CryptocurrencyItemEntity {
        return CryptocurrencyItemEntity(
            coinId = domain.id,
            name = domain.name,
            symbol = domain.symbol,
            price = domain.price,
            marketCap = domain.marketCap,
            imageUrl = domain.imageUrl
        )
    }
}