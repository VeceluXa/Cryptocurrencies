package com.danilovfa.cryptocurrencies.domain.model

data class CryptocurrencyChart(
    val id: String,
    val lastDay: List<ChartTimestamp>,
    val lastWeek: List<ChartTimestamp>,
    val lastMonth: List<ChartTimestamp>,
    val lastYear: List<ChartTimestamp>,
    val lastMax: List<ChartTimestamp>
)
