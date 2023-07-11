package com.danilovfa.cryptocurrencies.domain.model

import java.time.LocalDate

data class User(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate?
)
