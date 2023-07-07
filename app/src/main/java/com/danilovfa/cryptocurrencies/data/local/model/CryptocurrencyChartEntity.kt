package com.danilovfa.cryptocurrencies.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "charts")
data class CryptocurrencyChartEntity(
    @ColumnInfo(name = "chart_id")
    @PrimaryKey(autoGenerate = false)
    val chartId: String,
    @ColumnInfo(name = "last_day")
    val lastDay: List<ChartTimestampEntity>,
    @ColumnInfo(name = "last_week")
    val lastWeek: List<ChartTimestampEntity>,
    @ColumnInfo(name = "last_month")
    val lastMonth: List<ChartTimestampEntity>,
    @ColumnInfo(name = "last_year")
    val lastYear: List<ChartTimestampEntity>,
    @ColumnInfo(name = "last_max")
    val lastMax: List<ChartTimestampEntity>
)
