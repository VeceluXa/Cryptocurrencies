package com.danilovfa.cryptocurrencies.data.local.mapper

import com.danilovfa.cryptocurrencies.data.local.model.ChartTimestampEntity
import com.danilovfa.cryptocurrencies.utils.Mapper
import com.danilovfa.cryptocurrencies.domain.model.ChartTimestamp

class ChartTimestampEntityMapper : Mapper<ChartTimestampEntity, ChartTimestamp> {
    override fun fromEntity(entity: ChartTimestampEntity): ChartTimestamp {
        return ChartTimestamp(
            epochTimestamp = entity.epochTimestamp,
            price = entity.price
        )
    }

    override fun fromDomain(domain: ChartTimestamp): ChartTimestampEntity {
        return ChartTimestampEntity(
            epochTimestamp = domain.epochTimestamp,
            price = domain.price
        )
    }
}