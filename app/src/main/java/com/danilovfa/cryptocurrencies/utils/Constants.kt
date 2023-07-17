package com.danilovfa.cryptocurrencies.utils

class Constants {
    companion object {
        const val BASE_URL = "https://api.coingecko.com/api/v3/"
        const val VS_CURRENCY_DEFAULT = "usd"
        const val ORDER_CAPITALIZATION_ASCENDING = "market_cap_asc"
        const val ORDER_CAPITALIZATION_DESCENDING = "market_cap_desc"
        const val ORDER_VOLUME_ASCENDING = "volume_asc"
        const val ORDER_VOLUME_DESCENDING = "volume_desc"
        const val ORDER_ID_ASCENDING = "id_asc"
        const val ORDER_ID_DESCENDING = "id_desc"
        const val PRECISION_DEFAULT = 2
        const val PER_PAGE_DEFAULT = 20
        const val LOCALE_DEFAULT = "en"
        const val SAVE_FILE_BUFFER_SIZE = 4 * 1024 // 4 KB buffer (adjust the size as needed)
        const val ERROR_BODY_IS_NULL = "Body is null"
    }
}