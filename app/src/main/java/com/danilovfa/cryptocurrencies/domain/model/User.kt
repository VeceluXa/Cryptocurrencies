package com.danilovfa.cryptocurrencies.domain.model

import android.net.Uri
import java.time.LocalDate

data class User(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate?,
    val avatarUri: Uri
)
