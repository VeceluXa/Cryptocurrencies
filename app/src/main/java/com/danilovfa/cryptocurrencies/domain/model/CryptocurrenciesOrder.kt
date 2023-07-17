package com.danilovfa.cryptocurrencies.domain.model

import com.danilovfa.cryptocurrencies.utils.Constants.Companion.ORDER_CAPITALIZATION_ASCENDING
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.ORDER_CAPITALIZATION_DESCENDING
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.ORDER_ID_ASCENDING
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.ORDER_ID_DESCENDING
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.ORDER_VOLUME_ASCENDING
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.ORDER_VOLUME_DESCENDING

enum class CryptocurrenciesOrder(val message: String) {
    CAPITALIZATION_ASCENDING(ORDER_CAPITALIZATION_ASCENDING),
    CAPITALIZATION_DESCENDING(ORDER_CAPITALIZATION_DESCENDING),
    VOLUME_ASCENDING(ORDER_VOLUME_ASCENDING),
    VOLUME_DESCENDING(ORDER_VOLUME_DESCENDING),
    ID_ASCENDING(ORDER_ID_ASCENDING),
    ID_DESCENDING(ORDER_ID_DESCENDING)
}