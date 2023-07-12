package com.danilovfa.cryptocurrencies.app.viewmodel

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.domain.model.User
import com.danilovfa.cryptocurrencies.domain.model.ValidatorResponse
import com.danilovfa.cryptocurrencies.domain.usecase.GetUserUseCase
import com.danilovfa.cryptocurrencies.domain.usecase.SaveUserUseCase
import com.danilovfa.cryptocurrencies.domain.usecase.ValidateUserNameUseCase
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.SAVE_FILE_BUFFER_SIZE
import com.danilovfa.cryptocurrencies.utils.toFormattedString
import com.danilovfa.cryptocurrencies.utils.toLocalDate
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
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
            dateOfBirth = dateOfBirth.value.toLocalDate()
        )

        viewModelScope.launch {
            saveUserUseCase.execute(user)
        }
        return R.string.save_user_success
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

    fun saveGalleryAvatar(context: Context, uri: Uri): Boolean {
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = context.contentResolver.openOutputStream(getInternalAvatarUri(context))

        try {
            inputStream?.let { input ->
                outputStream?.let { output ->
                    val buffer = ByteArray(SAVE_FILE_BUFFER_SIZE)
                    var bytesRead: Int

                    while (input.read(buffer).also { bytesRead = it } != -1) output.write(
                        buffer,
                        0,
                        bytesRead
                    )

                    return true
                }
            }
        } catch (e: IOException) {
            // Handle the exception, e.g., show an error message
            e.printStackTrace()
        } finally {
            inputStream?.close()
            outputStream?.close()
        }

        return false
    }

    fun getInternalAvatarUri(context: Context): Uri {
        val tempImage = File(
            context.applicationContext.filesDir, context.getString(R.string.avatar_image_path)
        )
        return FileProvider.getUriForFile(
            context.applicationContext, context.getString(R.string.authorities), tempImage
        )
    }

    fun getUser() {
        viewModelScope.launch {
            viewModelScope.async { getUserUseCase.execute() }.await()?.let { user ->
                _firstName.value = user.firstName
                _lastName.value = user.lastName
                _dateOfBirth.value = user.dateOfBirth.toFormattedString()
            }
        }
    }
}