package com.danilovfa.cryptocurrencies.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class CryptocurrencyDetailsEntity(
    @Embedded
    val itemDetails: CryptocurrencyItemEntity,
    @Relation(
        parentColumn = "coin_id",
        entityColumn = "chart_id"
    )
    val charts: CryptocurrencyChartEntity
)
