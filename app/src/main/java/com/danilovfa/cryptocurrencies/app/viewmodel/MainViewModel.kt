package com.danilovfa.cryptocurrencies.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrenciesOrder
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyItem
import com.danilovfa.cryptocurrencies.domain.model.ResponseWrapper
import com.danilovfa.cryptocurrencies.domain.usecase.ClearCacheUseCase
import com.danilovfa.cryptocurrencies.domain.usecase.GetCryptocurrenciesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

class MainViewModel(
    private val clearCacheUseCase: ClearCacheUseCase,
    private val getCryptocurrenciesUseCase: GetCryptocurrenciesUseCase
) : ViewModel() {
    private val _sortOrder = MutableStateFlow(CryptocurrenciesOrder.CAPITALIZATION_DESCENDING)
    val sortOrder = _sortOrder.asStateFlow()

    private val _cryptocurrencies = MutableStateFlow<List<CryptocurrencyItem>>(listOf())
    val cryptocurrencies = _cryptocurrencies.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    private var pageToLoad = 0
    private var loadedPage = 0

    init {
        getNextPage()
    }

    fun changeSortOrder(order: CryptocurrenciesOrder) {
        _sortOrder.value = order
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            clearCacheUseCase.execute()
            loadedPage = 0
            pageToLoad = 0
            _cryptocurrencies.value = listOf()
            _isLoading.value = false
            getNextPage()
        }
    }

    fun getNextPage() {
        if (isLoading.value) return
        _isLoading.value = true

        pageToLoad += 1
        if (abs(pageToLoad - loadedPage) > 1) return

        viewModelScope.launch {
            getCryptocurrenciesUseCase.execute(pageToLoad, sortOrder.value)
                .collectLatest { response ->
                    when (response) {
                        is ResponseWrapper.Error -> {
                            _isLoading.value = false
                            _errorMessage.value = response.errorMessage
                        }

                        is ResponseWrapper.Loading -> {
                            _isLoading.value = true
                            _errorMessage.value = ""
                        }

                        is ResponseWrapper.Success -> {
                            _isLoading.value = false
                            _errorMessage.value = ""
                            val mutableList = _cryptocurrencies.value.toMutableList()
                            _cryptocurrencies.value = mutableList + response.data
                        }
                    }
                }
            loadedPage += 1
        }
    }
}