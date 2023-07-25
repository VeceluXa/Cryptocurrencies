package com.danilovfa.cryptocurrencies.domain.model

import androidx.annotation.StringRes
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.ORDER_CAPITALIZATION_ASCENDING
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.ORDER_CAPITALIZATION_DESCENDING
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.ORDER_ID_ASCENDING
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.ORDER_ID_DESCENDING
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.ORDER_VOLUME_ASCENDING
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.ORDER_VOLUME_DESCENDING

enum class CryptocurrenciesOrder(val apiMessage: String, @StringRes val resourceId: Int) {
    CAPITALIZATION_ASCENDING(ORDER_CAPITALIZATION_ASCENDING, R.string.order_capitalization_ascending),
    CAPITALIZATION_DESCENDING(ORDER_CAPITALIZATION_DESCENDING, R.string.order_capitalization_descending),
    VOLUME_ASCENDING(ORDER_VOLUME_ASCENDING, R.string.order_volume_ascending),
    VOLUME_DESCENDING(ORDER_VOLUME_DESCENDING, R.string.order_volume_descending),
    ID_ASCENDING(ORDER_ID_ASCENDING, R.string.order_id_ascending),
    ID_DESCENDING(ORDER_ID_DESCENDING, R.string.order_id_descending)
}