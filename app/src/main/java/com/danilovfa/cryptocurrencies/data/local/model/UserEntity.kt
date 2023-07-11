package com.danilovfa.cryptocurrencies.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate?
)