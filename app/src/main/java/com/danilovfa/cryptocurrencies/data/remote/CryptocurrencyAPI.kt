package com.danilovfa.cryptocurrencies.data.remote

import com.danilovfa.cryptocurrencies.data.remote.dto.ChartDto
import com.danilovfa.cryptocurrencies.data.remote.dto.CryptocurrenciesItemDto
import com.danilovfa.cryptocurrencies.data.remote.dto.CryptocurrencyDetailsDto
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.LOCALE_DEFAULT
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.PER_PAGE_DEFAULT
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.PRECISION_DEFAULT
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.VS_CURRENCY_DEFAULT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptocurrencyAPI {

    /**
     * @param id Pass the coin id (can be obtained from /coins) eg. bitcoin
     * @param days Data up to number of days ago (eg. 1,14,30,max)
     * @param precision full or any value between 0 - 18 to specify decimal place for currency price value
     * @param vsCurrency The target currency of market data (usd, eur, jpy, etc.)
     */
    @GET("coins/{id}/market_chart")
    fun getChart(
        @Path("id") id: String,
        @Query("days") days: String,
        @Query("precision") precision: Int = PRECISION_DEFAULT,
        @Query("vs_currency") vsCurrency: String = VS_CURRENCY_DEFAULT
    ): Response<ChartDto>

    /**
     * @param id Pass the coin id (can be obtained from /coins) eg. bitcoin
     * @param localization Include all localized languages in response (true/false)
     * @param tickers Include tickers data (true/false)
     * @param marketData Include market_data (true/false)
     * @param communityData Include community_data data (true/false)
     * @param developerData Include developer_data data (true/false)
     * @param sparkline Include sparkline 7 days data (eg. true, false)
     */
    @GET("coins/{id}")
    fun getCryptocurrencyDetails(
        @Path("id") id: String,
        @Query("localization") localization: Boolean = false,
        @Query("tickers") tickers: Boolean = false,
        @Query("market_data") marketData: Boolean = true,
        @Query("community_data") communityData: Boolean = false,
        @Query("developer_data") developerData: Boolean = false,
        @Query("sparkline") sparkline: Boolean = false
    ): Response<CryptocurrencyDetailsDto>

    /**
     * @param vsCurrency The target currency of market data (usd, eur, jpy, etc.)
     * @param order Valid values: market_cap_asc, market_cap_desc, volume_asc, volume_desc,
     * id_asc, id_desc sort results by field.
     * @param perPage Valid values: 1..250. Total results per page
     * @param page Page through results
     * @param sparkline Include sparkline 7 days data (eg. true, false)
     * @param locale Valid values: ar, bg, cs, da, de, el, en, es, fi, fr, he, hi, hr, hu, id,
     * it, ja, ko, lt, nl, no, pl, pt, ro, ru, sk, sl, sv, th, tr, uk, vi, zh, zh-tw
     * @param precision Full or any value between 0 - 18 to specify decimal place for
     * currency price value
     */
    @GET("coins/markets")
    fun getCryptocurrenciesByPage(
        @Query("vs_currency") vsCurrency: String = VS_CURRENCY_DEFAULT,
        @Query("order") order: String,
        @Query("per_page") perPage: Int = PER_PAGE_DEFAULT,
        @Query("page") page: Int,
        @Query("sparkline") sparkline: Boolean = false,
        @Query("locale") locale: String = LOCALE_DEFAULT,
        @Query("precision") precision: Int = PRECISION_DEFAULT
    ): Response<List<CryptocurrenciesItemDto>>
}