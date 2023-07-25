package com.danilovfa.cryptocurrencies.data.local.model

import androidx.room.ColumnInfo

data class ChartTimestampEntity(
    @ColumnInfo(name = "epoch_timestamp")
    val epochTimestamp: Long,
    val price: Double
)
