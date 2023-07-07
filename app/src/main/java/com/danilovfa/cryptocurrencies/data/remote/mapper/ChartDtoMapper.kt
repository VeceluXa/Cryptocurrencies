package com.danilovfa.cryptocurrencies.data.remote.mapper

import com.danilovfa.cryptocurrencies.data.remote.dto.ChartDto
import com.danilovfa.cryptocurrencies.domain.model.ChartTimestamp
import com.danilovfa.cryptocurrencies.utils.Mapper

class ChartDtoMapper : Mapper<ChartDto, List<ChartTimestamp>> {
    override fun fromEntity(entity: ChartDto): List<ChartTimestamp> {
        return entity.prices.map {
            ChartTimestamp(it[0].toLong(), it[1])
        }
    }

    override fun fromDomain(domain: List<ChartTimestamp>): ChartDto {
        TODO("Not yet implemented")
    }
}