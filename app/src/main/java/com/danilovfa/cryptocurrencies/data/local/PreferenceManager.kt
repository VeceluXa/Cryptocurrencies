package com.danilovfa.cryptocurrencies.data.local

import android.content.Context
import android.net.Uri
import com.danilovfa.cryptocurrencies.domain.model.User
import java.time.LocalDate
import java.time.format.DateTimeParseException

class PreferenceManager(
    context: Context
) {
    private val prefs = context.getSharedPreferences("user_settings", Context.MODE_PRIVATE)

    fun getUser(): User {
        val date: LocalDate? = try {
            LocalDate.parse(prefs.getString("date", ""))
        } catch (e: DateTimeParseException) {
            null
        }

        return User(
            firstName = prefs.getString("first_name", "") ?: "",
            lastName = prefs.getString("last_name", "") ?: "",
            dateOfBirth = date,
            avatarUri = Uri.parse(prefs.getString("avatar_uri", ""))
        )
    }

    fun saveUser(user: User) {
        prefs.edit().apply {
            putString("first_name", user.firstName)
            putString("last_name", user.lastName)
            putString("date", user.dateOfBirth?.toString() ?: "")
            putString("avatar_uri", user.avatarUri.toString())
            apply()
        }
    }
}