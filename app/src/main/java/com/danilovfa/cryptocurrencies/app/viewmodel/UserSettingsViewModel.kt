package com.danilovfa.cryptocurrencies.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.domain.model.User
import com.danilovfa.cryptocurrencies.domain.model.ValidatorResponse
import com.danilovfa.cryptocurrencies.domain.usecase.GetUserUseCase
import com.danilovfa.cryptocurrencies.domain.usecase.SaveUserUseCase
import com.danilovfa.cryptocurrencies.domain.usecase.ValidateUserNameUseCase
import kotlinx.coroutines.launch

class UserSettingsViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val validateUserNameUseCase: ValidateUserNameUseCase
) : ViewModel() {

    fun saveUser(user: User): Int {
        val isFirstNameValid = validateUserNameUseCase.execute(user.firstName)
        val isLastNameValid = validateUserNameUseCase.execute(user.lastName)

        if (isFirstNameValid is ValidatorResponse.Error) {
            return isFirstNameValid.messageId
        }
        if (isLastNameValid is ValidatorResponse.Error) {
            return isLastNameValid.messageId
        }
        viewModelScope.launch {
            saveUserUseCase.execute(user)
        }
        return R.string.save_user_success
    }
}