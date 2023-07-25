package com.danilovfa.cryptocurrencies.app.viewmodel

import com.danilovfa.cryptocurrencies.domain.usecase.GetCryptocurrencyDetailsUseCase
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class DetailsViewModelTest {

    private val getDetailsUseCase: GetCryptocurrencyDetailsUseCase = mockk()
    private val viewModel = DetailsViewModel(detailsUseCase = getDetailsUseCase)

    @Test
    fun priceChangedToString() {
        var expected = "+32.42 %"
        var actual = viewModel.priceChangedToString(32.42)
        assertEquals(expected, actual)

        expected = "-0.30 %"
        actual = viewModel.priceChangedToString(-0.3000)
        assertEquals(expected, actual)
    }

    @Test
    fun priceToString() {
        val expected = "324.32 $"
        val actual = viewModel.priceToString(324.32)
        assertEquals(expected, actual)
    }

    @Test
    fun suffixPriceToString() {
        var expected = "$ 542.32 B"
        var actual = viewModel.suffixPriceToString(542_324_000_000.0)
        assertEquals(expected, actual)

        expected = "$ 32.42 K"
        actual = viewModel.suffixPriceToString(32_423.0)
        assertEquals(expected, actual)

        expected = "$ 24.32 M"
        actual = viewModel.suffixPriceToString(24_321_321.32)
        assertEquals(expected, actual)
    }
}