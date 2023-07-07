package com.danilovfa.cryptocurrencies.data.repository

import com.danilovfa.cryptocurrencies.data.local.PreferenceManager
import com.danilovfa.cryptocurrencies.domain.model.User
import com.danilovfa.cryptocurrencies.domain.repository.UserRepository

class UserRepositoryImpl(
    private val preferenceManager: PreferenceManager
) : UserRepository {
    override fun getUser(): User {
        return preferenceManager.getUser()
    }

    override fun saveUser(user: User) {
        preferenceManager.saveUser(user)
    }
}