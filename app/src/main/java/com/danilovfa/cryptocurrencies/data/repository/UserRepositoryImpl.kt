package com.danilovfa.cryptocurrencies.data.repository

import com.danilovfa.cryptocurrencies.data.local.CryptocurrencyDao
import com.danilovfa.cryptocurrencies.data.local.mapper.UserEntityMapper
import com.danilovfa.cryptocurrencies.domain.model.User
import com.danilovfa.cryptocurrencies.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dao: CryptocurrencyDao
) : UserRepository {
    private val userMapper = UserEntityMapper()
    override suspend fun getUser(): User? {
        var user: User? = null
        withContext(ioDispatcher) {
            dao.getUser(0)?.let { entity ->
                user = userMapper.fromEntity(entity)
            }
        }
        return user
    }

    override suspend fun saveUser(user: User) {
        withContext(ioDispatcher) {
            dao.saveUser(userMapper.fromDomain(user))
        }
    }
}