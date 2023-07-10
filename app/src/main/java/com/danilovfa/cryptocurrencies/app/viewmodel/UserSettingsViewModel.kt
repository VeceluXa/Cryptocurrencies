package com.danilovfa.cryptocurrencies.app.viewmodel

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.domain.model.User
import com.danilovfa.cryptocurrencies.domain.model.ValidatorResponse
import com.danilovfa.cryptocurrencies.domain.usecase.GetUserUseCase
import com.danilovfa.cryptocurrencies.domain.usecase.SaveUserUseCase
import com.danilovfa.cryptocurrencies.domain.usecase.ValidateUserNameUseCase
import com.danilovfa.cryptocurrencies.utils.TAG
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar
import java.util.Locale

class UserSettingsViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val validateUserNameUseCase: ValidateUserNameUseCase
) : ViewModel() {

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName

    private val _dateOfBirth = MutableStateFlow("")
    val dateOfBirth: StateFlow<String> = _dateOfBirth

    private val _avatarUri = MutableStateFlow(Uri.EMPTY)
    val avatarUri: StateFlow<Uri> = _avatarUri

    fun saveUser(): Int {
        val isFirstNameValid = validateUserNameUseCase.execute(firstName.value)
        val isLastNameValid = validateUserNameUseCase.execute(lastName.value)

        if (isFirstNameValid is ValidatorResponse.Error) {
            return isFirstNameValid.messageId
        }
        if (isLastNameValid is ValidatorResponse.Error) {
            return isLastNameValid.messageId
        }

        val user = User(
            firstName = firstName.value,
            lastName = lastName.value,
            dateOfBirth = strToLocalDate(dateOfBirth.value),
            avatarUri = avatarUri.value
        )

        viewModelScope.launch {
            saveUserUseCase.execute(user)
        }
        return R.string.save_user_success
    }

    // Format dd.mm.yyyy
    private fun strToLocalDate(str: String): LocalDate? {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        var localDate: LocalDate? = null
        try {
            localDate = LocalDate.parse(str, formatter)
            Log.d(TAG, "Date: $localDate")
        } catch(e: DateTimeParseException) {
            Log.e(TAG, "Failed to parse LocalDate")
        }
        return localDate
    }

    private fun localDateToStr(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        return date?.format(formatter) ?: ""
    }

    fun saveFirstName(name: String) {
        _firstName.value = name
    }

    fun saveLastName(name: String) {
        _lastName.value = name
    }

    fun saveDateOfBirth(epoch: Long) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = epoch
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.US)
        _dateOfBirth.value = format.format(calendar.time)
    }

    fun saveAvatar(uri: Uri) {
        _avatarUri.value = uri
    }

    fun getUser() {
        viewModelScope.launch {
            viewModelScope.async { getUserUseCase.execute() }.await()?.let { user ->
                _firstName.value = user.firstName
                _lastName.value = user.lastName
                _dateOfBirth.value = localDateToStr(user.dateOfBirth)
                _avatarUri.value = user.avatarUri
            }
        }
    }
}